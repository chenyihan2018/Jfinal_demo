package com.jslx.register.interceptor.parent;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.log.Log;
import com.jslx.register.controller.ControllerBase;
import com.jslx.register.entity.RedisParent;

/**
 * @author: lybing
 * @create: 2018-12-13 17:02
 * @desc:
 */

public class ParentAuthInterceptor implements Interceptor {
    private static final Log LOG = Log.getLog(ParentAuthInterceptor.class);


    @Override
    public void intercept(Invocation ai) {
        ControllerBase<RedisParent> controller = (ControllerBase<RedisParent>) ai.getController();
        RedisParent currentUser = controller.getCurrentUser(RedisParent.class);
        if (currentUser == null){
            LOG.info("【     尚未登陆！】");
            controller.redirect("/parent/home/toLogin");
        }
    }
}
