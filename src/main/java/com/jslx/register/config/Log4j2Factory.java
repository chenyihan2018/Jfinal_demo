package com.jslx.register.config;

import com.jfinal.log.ILogFactory;
import com.jfinal.log.Log;

/**
 * @author: lybing
 * @create: 2018-12-12 14:53
 * @desc: log4j2 Factory
 */

public class Log4j2Factory implements ILogFactory {
    @Override
    public Log getLog(Class<?> aClass) {
        return new Log4j2Log(aClass);
    }

    @Override
    public Log getLog(String s) {
        return new Log4j2Log(s);
    }
}
