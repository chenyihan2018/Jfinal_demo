package com.jslx.register.shiro;/**
 * Created by chenjia on 2018/12/17.
 */

import com.jslx.register.model.ParUser;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @author chenjia
 * @create 2018-12-17 15:37
 * @desc
 **/
public class ShiroRealm extends AuthorizingRealm {


    /**
     * 验证用户权限
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        ParUser userInPrincipal = (ParUser) principals.getPrimaryPrincipal();
        //根据用户获取权限
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole("user");
        return info;
    }

    /**
     * 验证用户登录
     * @param authcToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        ParUser parUser = ParUser.dao.findFirst("SELECT * FROM par_user WHERE idcard=?",token.getUsername());
        if (parUser != null) {
            if(!parUser.getPassword().equals(String.valueOf(token.getPassword()))){
                throw new AuthenticationException("密码错误");
            }
            return new SimpleAuthenticationInfo(parUser, parUser.getPassword(),parUser.getMobile());
        } else {
            throw new AuthenticationException("用户不存在");
        }
    }

}
