package com.jslx.register;/**
 * Created by chenjia on 2018/12/13.
 */

import com.jfinal.server.undertow.UndertowServer;
import com.jslx.register.config.RegisterConfig;

/**
 * @author chenjia
 * @create 2018-12-13 9:53
 * @desc
 **/
public class RegisterMainStart {

    /**
     *  main 方法启动项目,默认端口 : 80
     * @param args
     */
    public static void main(String[] args) {
        UndertowServer.start(RegisterConfig.class);
//        UndertowServer.start(RegisterConfig.class,8089,false);
    }

}
