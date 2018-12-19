package com.jslx.register.config;

import com.jfinal.log.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author: lybing
 * @create: 2018-12-12 14:46
 * @desc: log4j2 logging
 */

public class Log4j2Log extends Log {
    private Logger log;

    public Log4j2Log(Class<?> clazz){
        log = LogManager.getLogger(clazz);
    }

    public Log4j2Log(String name){
        log = LogManager.getLogger(name);
    }

    @Override
    public void debug(String s) {
        log.debug(s);
    }

    @Override
    public void debug(String s, Throwable throwable) {
        log.debug(s, throwable);
    }

    @Override
    public void info(String s) {
        log.info(s);
    }

    @Override
    public void info(String s, Throwable throwable) {
        log.info(s, throwable);
    }

    @Override
    public void warn(String s) {
        log.warn(s);
    }

    @Override
    public void warn(String s, Throwable throwable) {
        log.warn(s, throwable);
    }

    @Override
    public void error(String s) {
        log.error(s);
    }

    @Override
    public void error(String s, Throwable throwable) {
        log.error(s, throwable);
    }

    @Override
    public void fatal(String s) {
        log.fatal(s);
    }

    @Override
    public void fatal(String s, Throwable throwable) {
        log.fatal(s, throwable);
    }

    @Override
    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    @Override
    public boolean isInfoEnabled() {
        return log.isInfoEnabled();
    }

    @Override
    public boolean isWarnEnabled() {
        return log.isWarnEnabled();
    }

    @Override
    public boolean isErrorEnabled() {
        return log.isErrorEnabled();
    }

    @Override
    public boolean isFatalEnabled() {
        return log.isFatalEnabled();
    }
}
