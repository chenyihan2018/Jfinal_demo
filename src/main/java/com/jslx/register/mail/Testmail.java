package com.jslx.register.mail;/**
 * Created by chenjia on 2018/12/18.
 */

import com.jfplugin.mail.MailKit;
import com.jfplugin.mail.core.MailSender;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenjia
 * @create 2018-12-18 14:11
 * @desc
 **/
public class Testmail {

    public static void sendMail(){
        MailKit.send("782529153@qq.com",null,"测试邮件","测试内容");
    }


    public static void sendTemplteMail(){
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("var1","变量1");
        dataMap.put("var2","变量2");
    }

}
