package com.jslx.register.controller.parent;

import com.jfinal.aop.Clear;
import com.jfinal.kit.AesKit;
import com.jfinal.kit.HashKit;
import com.jfinal.plugin.activerecord.Db;
import com.jslx.register.common.verify.IDCardVarify;
import com.jslx.register.interceptor.parent.ParentAuthInterceptor;

/**
 * @author: lybing
 * @create: 2018-12-13 21:40
 * @desc:
 */

public class ParentHomeController extends ParentAuthController{


    public void index(){
        renderFreeMarker("/WEB-INF/templates/home.html");
    }

    @Clear(ParentAuthInterceptor.class)
    public void login(){
        // todo 登录,2018年12月14日09:23:34
        throw new RuntimeException("to do");
    }

    @Clear(ParentAuthInterceptor.class)
    public void toLogin(){
        renderFreeMarker("/WEB-INF/templates/login.html");
    }

    @Clear(ParentAuthInterceptor.class)
    public void regist(){
        Integer grade = getParaToInt("grade");
        String idcard = getPara("idcard");
        String code = getPara("code");
        String mobile = getPara("mobile");
        String password = getPara("password");
        // 参数校验及处理

        int sex = IDCardVarify.getSex(idcard);
        String birthString = IDCardVarify.getBirthString(idcard);
        // MD5
        password = HashKit.md5(password);
        // 查看是否已注册

        // 存入数据库
        Db.update("INSERT INTO par_user(idcard,mobile,password,birth,sex,grade,create_time) VALUES(?,?,?,?,?,?,NOW())",
                idcard, mobile, password, birthString, sex, grade);
        forwardAction("/parent/home/toLogin");
    }

    @Clear(ParentAuthInterceptor.class)
    public void toRegist(){
        renderFreeMarker("/WEB-INF/templates/regist.html");
    }
}
