package com.jslx.register.controller.parent;/**
 * Created by chenjia on 2018/12/14.
 */

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jslx.register.common.redis.RedisTool;
import com.jslx.register.common.verify.IDCardVarify;
import com.jslx.register.model.ParUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;

/**
 * @author chenjia
 * @create 2018-12-14 11:38
 * @desc
 **/
public class ParentLoginController extends ParenthController {

    /**
     * 家长用户登录
     */
    public void  login(){
        // type = 1:身份证号     type=2 手机号登录
        Integer type = getParaToInt("type",1);
        String mobile = getPara("mobile");
        String idcard = getPara("idcard");
        String password = getPara("password");
        String code = getPara("code");
        int status =0;
        String result = "操作成功";
        if(type.intValue() == 1) {
            //身份证号登录
            ParUser parUser = ParUser.dao.findFirst("SELECT * FROM par_user WHERE idcard=? AND PASSWORD=?", idcard, password);
            if(parUser == null){
                status = 1;
                result = "用户名或密码不对，请重新输入！";
            }else {
                RedisTool.setObject2RedisExpire(getUUID(),parUser,3600*24*7);
            }
        }else {
            //手机号码登录
            Record record = Db.findFirst("SELECT * FROM sys_verify_code WHERE mobile=?  AND `code`=? AND source='student'", mobile, code);
            if(record==null){
                status = 1;
                result = "用户名或密码不对，请重新输入！";
            }else {
                ParUser parUser = ParUser.dao.findFirst("SELECT * FROM par_user WHERE mobile=?", mobile);
                RedisTool.setObject2RedisExpire(getUUID(),parUser,3600*24*7);
            }
        }
        setAttr("status",status);
        setAttr("result",result);
        returnJson();
    }

    /**
     * 家长用户登录
     */
    public void  loginShiro(){
        // type = 1:身份证号     type=2 手机号登录
        Integer type = getParaToInt("type",1);
        String mobile = getPara("mobile");
        String idcard = getPara("idcard");
        String password = getPara("password");
        String code = getPara("code");
        int status =0;
        String result = "操作成功";
        if(type.intValue() == 1) {
            //身份证号登录
            UsernamePasswordToken token = new UsernamePasswordToken(idcard,password);
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.login(token);
                System.out.println("success ...login....");
                ParUser parUser = ParUser.dao.findFirst("SELECT * FROM par_user WHERE idcard=?", idcard);
                RedisTool.setObject2RedisExpire(getUUID(),parUser,3600*24*7);
            } catch (AuthenticationException e) {
                //虽然在realm中有具体的错误信息，但是安全起见，统一返回登录失败
                System.out.println("faild ...login....");
            }
        }else {
            //手机号码登录
            Record record = Db.findFirst("SELECT * FROM sys_verify_code WHERE mobile=?  AND `code`=? AND source='student'", mobile, code);
            if(record==null){
                status = 1;
                result = "用户名或密码不对，请重新输入！";
            }else {
                ParUser parUser = ParUser.dao.findFirst("SELECT * FROM par_user WHERE mobile=?", mobile);
                RedisTool.setObject2RedisExpire(getUUID(),parUser,3600*24*7);
            }
        }
        setAttr("status",status);
        setAttr("result",result);
        returnJson();
    }

    /**
     * 手机号获取验证码
     */
    public void getMobileCode(){
        String mobile = getPara("mobile");

    }


}
