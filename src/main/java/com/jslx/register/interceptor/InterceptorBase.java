package com.jslx.register.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Action;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jslx.register.config.RegisterConfig;
import com.kabao.ext.kit.CookieKit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.UUID;

/**
 * 拦截器基类
 * @ClassName InterceptorBase
 * @Description 拦截器基类
 * @author Ryan
 */
public abstract class InterceptorBase implements Interceptor {
	

	private static Log log = Log.getLog(InterceptorBase.class);
	/**
	 * UUIDNAME COOKIE名称
	 */
	private String UUIDNAME;
	public String getUUIDNAME() {
		return UUIDNAME;
	}
	public void setUUIDNAME(String uUIDNAME) {
		UUIDNAME = uUIDNAME;
	}
	/**
	 * COOKIEEXPIRETIME 分钟
	 */
	private int COOKIEEXPIRETIME;
	public int getCOOKIEEXPIRETIME() {
		return COOKIEEXPIRETIME;
	}
	public void setCOOKIEEXPIRETIME(int cOOKIEEXPIRETIME) {
		COOKIEEXPIRETIME = cOOKIEEXPIRETIME;
	}
	/**
	 * DOMAIN 域
	 */
	private String DOMAIN;
	public String getDOMAIN() {
		return DOMAIN;
	}
	public void setDOMAIN(String dOMAIN) {
		DOMAIN = dOMAIN;
	}
	/**
	 * ATTRIBUTENAME 属性名称
	 */
	private String ATTRIBUTENAME;
	public String getATTRIBUTENAME() {
		return ATTRIBUTENAME;
	}
	public void setATTRIBUTENAME(String aTTRIBUTENAME) {
		ATTRIBUTENAME = aTTRIBUTENAME;
	}
	public InterceptorBase(){
		init();
	}
	/**
	 * 子类必须实现
	 */
	public abstract void init();

	@Override
	public void intercept(Invocation ai) {
		Controller controller = ai.getController();
		String cookieValue;
		UUID uuid = UUID.randomUUID();
		//根据cookie的名字获得一个cookie
		Cookie cookieUUID = CookieKit.getCookie(controller.getRequest(), this.getUUIDNAME());
		if(cookieUUID == null){
			//如果是第一次登入的话,则添加一个名字为uuidname的cookie,这个cookie的值为一个randomuuid;
			CookieKit.addCookie(controller.getResponse(), this.getUUIDNAME(), uuid.toString(), this.getCOOKIEEXPIRETIME(), "/", this.getDOMAIN());
			cookieValue = uuid.toString();
		}else{
			cookieValue = cookieUUID.getValue();
		}
		//把cookie的值存入和一个常量存入到request域中
		controller.getRequest().setAttribute(this.getATTRIBUTENAME(), cookieValue);
		controller.getRequest().setAttribute("resource", RegisterConfig.CDN_RESOURCES);
		try {
			ai.invoke();
		} catch (Exception e) {
			//报警描述编辑
			String redirctUrl = controller.getRequest().getHeader("referer");
	        if(StrKit.isBlank(redirctUrl)){
	          redirctUrl = controller.getRequest().getRequestURI();
	        }
	        String msg = "您的网络不给力呀";
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace();
			e.printStackTrace(pw);
			Db.update("INSERT INTO sys_exception_log (exception,resource,request_uri,request_para,package_name,create_time) " +
					"VALUES (?,?,?,?,?,?)",sw.toString()+report(controller,null),
					RegisterConfig.CDN_RESOURCES,redirctUrl,controller.getPara(),
					RegisterConfig.ROUTES.get(controller.getClass().getPackage().getName()+""),new Date());
			log.error("业务逻辑代码遇到异常时保存日志:",e);
			Enumeration<String> enus = controller.getAttrNames();
			while (enus.hasMoreElements()) {
				controller.removeAttr(enus.nextElement());
			}
			controller.setAttr("status", 1);
			controller.setAttr("result", msg);
			errorRenderWrapper(controller);
		} finally {
			
		}
	}
	/**
	 * 错误 Render 包装器
	 * @param controller
	 */
	public abstract void errorRenderWrapper(Controller controller);

	private static final ThreadLocal<SimpleDateFormat> sdf = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

	public static final String report(Controller controller, Action action) {
		StringBuilder sb = new StringBuilder("\nJFinal action report -------- ").append((sdf.get()).format(new Date())).append(" ------------------------------\n");
		String urlParas = controller.getPara();
		if (urlParas != null) {
			sb.append("UrlPara     : ").append(urlParas).append("\n");
		}

		HttpServletRequest request = controller.getRequest();
		Enumeration e = request.getParameterNames();
		if (e.hasMoreElements()) {
			sb.append("Parameter   : ");
			while (e.hasMoreElements()) {
				String name = (String) e.nextElement();
				String[] values = request.getParameterValues(name);
				if (values.length == 1) {
					sb.append(name).append("=").append(values[0]);
				} else {
					sb.append(name).append("[]={");
					for (int i = 0; i < values.length; i++) {
						if (i > 0) {
							sb.append(",");
						}
						sb.append(values[i]);
					}
					sb.append("}");
				}
				sb.append("  ");
			}
			sb.append("\n");
		}
		sb.append("--------------------------------------------------------------------------------\n");
		System.out.print(sb.toString());
		return sb.toString();
	}
}
