package com.jslx.register.interceptor.parent;

import com.jfinal.core.Controller;
import com.jslx.register.config.RegisterConfig;
import com.jslx.register.interceptor.InterceptorBase;

/**
 * @author: lybing
 * @create: 2018-12-13 16:39
 * @desc:
 */

public class ParentInterceptor extends InterceptorBase {
    @Override
    public void init() {
        this.setATTRIBUTENAME(RegisterConfig.COOKIE_KEY_PREFIX + "_parent");
        this.setCOOKIEEXPIRETIME(0);
        this.setDOMAIN("");
        this.setUUIDNAME(RegisterConfig.COOKIE_KEY_PREFIX + "_parent");
    }

    @Override
    public void errorRenderWrapper(Controller controller) {
        controller.renderJson();
    }
}
