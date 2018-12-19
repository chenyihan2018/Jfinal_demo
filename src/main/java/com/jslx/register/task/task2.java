package com.jslx.register.task;/**
 * Created by chenjia on 2018/12/18.
 */

import com.jfinal.plugin.cron4j.ITask;

/**
 * @author chenjia
 * @create 2018-12-18 11:29
 * @desc
 **/
public class task2 implements ITask {
    @Override
    public void run() {
        System.out.println("定时任务2 ---- 执行了");
    }

    @Override
    public void stop() {

    }
}
