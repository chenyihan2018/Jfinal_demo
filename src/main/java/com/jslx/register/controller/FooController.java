package com.jslx.register.controller;

import com.jfinal.core.Controller;

/**
 * @author: lybing
 * @create: 2018-12-12 13:56
 * @desc:
 */

public class FooController extends Controller {

    public void index(){
        renderJson();
    }

    public void captcha(){
        renderCaptcha();
    }


    public void toFreemarker(){
        setAttr("name","chenjia");
        this.renderFreeMarker("/WEB-INF/templates/Testfreemarker.html");
    }
}
