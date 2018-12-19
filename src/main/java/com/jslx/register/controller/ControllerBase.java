package com.jslx.register.controller;

import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;
import com.jfinal.core.NotAction;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;
import com.jfinal.plugin.redis.Redis;
import com.jslx.register.common.redis.RedisTool;
import com.jslx.register.config.RegisterConfig;
import org.apache.logging.log4j.util.Strings;

public abstract class ControllerBase<T> extends Controller {
	private static final Log LOG = Log.getLog(ControllerBase.class);
	/**
	 * 设置  uuid 对应 attribute name
	 */
	private String GLOBALATTRIBUTENAME;
	private String ROUTEPATH;

	public ControllerBase(){
		init();
	}
	public abstract void init();

	public T getCurrentUser(Class<T> clazz){
		String uuid = this.getUUID();
		LOG.info(String.format("【		从redis获取当前登录用户‘%s’】", uuid));
//		Object result = Redis.use().get(uuid);
		Object result =  RedisTool.getObjectfromRedis(uuid);
		if (result != null && Strings.isNotBlank(result.toString())){
			T t = JSON.parseObject(result.toString(), clazz);
			return t;
		}
		return null;
	}

	/**
	 * 渲染Json返回
	 */
	@NotAction
	public void returnJson(){
		returnJson(null);
	}

	@NotAction
	public void returnJson(String json){
		String origin = getRequest().getHeader("origin");
		if(!StrKit.isBlank(origin)){
			if (origin.indexOf(RegisterConfig.PROJECT_DOMAIN) != -1){
				this.getResponse().addHeader("Access-Control-Allow-Origin", origin);
			}else{
				this.getResponse().addHeader("Access-Control-Allow-Origin", null);
			}
		}
		if (json == null) {
			this.renderJson();
			return;
		}
		this.renderJson(json);
	}

	@NotAction
	public String getResult(String key){
		return RegisterConfig.STATUS_CODE_MAP.get(key)==null?
				"没有"+key+"对应的状态码":
				RegisterConfig.STATUS_CODE_MAP.get(key);
	}


	@NotAction
	public String getResult(int status){
		return getResult(status + "");
	}

	public String getUUID(){
		return this.getRequest().getAttribute(this.GLOBALATTRIBUTENAME)==null?
				"":
				this.getRequest().getAttribute(this.GLOBALATTRIBUTENAME).toString();
	}

	public String getGLOBALATTRIBUTENAME() {
		return GLOBALATTRIBUTENAME;
	}
	public void setGLOBALATTRIBUTENAME(String gLOBALATTRIBUTENAME) {
		GLOBALATTRIBUTENAME = gLOBALATTRIBUTENAME;
	}
	public String getROUTEPATH() {
		return ROUTEPATH;
	}
	public void setROUTEPATH(String rOUTEPATH) {
		ROUTEPATH = rOUTEPATH;
	}
}
