package com.jslx.register.controller.parent;

import com.jfinal.aop.Before;
import com.jslx.register.interceptor.parent.ParentAuthInterceptor;
import com.jslx.register.interceptor.parent.ParentInterceptor;

/**
 * @author: lybing
 * @create: 2018-12-13 18:04
 * @desc:
 */

@Before({
        ParentInterceptor.class,
        ParentAuthInterceptor.class})
public class ParentAuthController extends ParenthController {

}
