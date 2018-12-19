package com.jslx.register.controller.parent;

import com.jslx.register.config.RegisterConfig;
import com.jslx.register.controller.ControllerBase;

/**
 * @author: lybing
 * @create: 2018-12-13 14:06
 * @desc: 家长controller
 */

public class ParenthController extends ControllerBase {

    @Override
    public void init() {
        this.setGLOBALATTRIBUTENAME(RegisterConfig.COOKIE_KEY_PREFIX + "_parent");
        this.setROUTEPATH(RegisterConfig.ROUTES.get(this.getClass().getPackage().getName()));
    }
}
