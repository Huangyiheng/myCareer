package com.hyh.utility;/*
package com.maxwisdom.parallel.utility;

import com.maxwisdom.parallel.dao.AdminUsersDao;
import com.maxwisdom.parallel.entity.AdminUsers;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

*/
/*自定义*//*

public class CustomRealm extends AuthorizingRealm {
    public void setName(String name) {
        super.setName("customRealm");

    }


    @Autowired
    private AdminUsersDao admuserdao;

    */
/*授权方法
     * 操作的时候,判断用户是否具有
     *
     * *//*

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        AdminUsers user = (AdminUsers) principalCollection.getPrimaryPrincipal();//得到唯一的安全数据
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> roles = new HashSet<String>();//所有角色
        Set<String> perms = new HashSet<String>();//所有权限

      */
/*  for (Role role : user.getRole()) {
            roles.add(role.getName());
            for (Permission perm :role.getPermissions()) {
                perms.add(perm.getCode());
            }
            info.setStringPermissions(perms);
            info.setRoles(roles);
            return info;
        }*//*



        return null;
    }

    */
/*认证方法
     * //参数,传递用户名和密码
     * *//*

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1.获取登录用户名密码(token)
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        String username = upToken.getUsername();
        String password = new String(upToken.getPassword());


        //2.根据用户名查询数据库
        AdminUsers user = admuserdao.findByUsername(username);

        //3.判断用户是否存在或者密码是否一致
        //密码加密,md5加密shiro已经提供好了
        password = new Md5Hash(password, "hyhMAXWISDOM", 3).toString();
        //4.如果一致返回安全数据
        if (user != null && user.getPassword().equals(password)) {
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());

        }


        //5.不一致返回null


        return null;
    }


}
*/
