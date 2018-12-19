package com.jslx.register.task;/**
 * Created by chenjia on 2018/12/18.
 */

import com.jfinal.plugin.cron4j.ITask;
import com.jslx.register.mail.Testmail;

/**
 * @author chenjia
 * @create 2018-12-18 11:29
 * @desc
 **/
public class task1 implements ITask {
    @Override
    public void run() {
        System.out.println("定时任务1 ---- 执行了");
        Testmail.sendMail();
    }

    @Override
    public void stop() {

    }
}
