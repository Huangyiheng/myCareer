package com.hyh.shiro;

import com.maxwisdom.parallel.entity.Permission;
import com.maxwisdom.parallel.entity.Role;
import com.maxwisdom.parallel.entity.User;
import com.maxwisdom.parallel.service.PermissionService;
import com.maxwisdom.parallel.service.UserService;
import com.maxwisdom.parallel.utility.Encrypt;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserCustomRealm  extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;

    /**
     * 授权
     * 操作的时候,判断用户是否具有相应的权限
     * 先认证安全数据
     * 再授权,根据安全数据获取用户具有的所有操作权限
     *  //1.获取当前登录的用户
     *         User user = (User)parm.fromRealm(this.getName()).iterator().next();
     *
     *         //获取当前用户可以操作的所有模块
     *         List<Module> modules = moduleService.findModulesByUserId(user.getId());
     *
     *         Set<String> set = new HashSet<>();
     *         for (Module module : modules) {
     *             set.add(module.getName());
     *         }
     *         SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
     *         info.setStringPermissions(set);
     *         return info;
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection parm) {
        System.out.println("执行了授权 doGetAuthorizationInfo");

        //1.获取已认证的用户数据
        User user =(User) parm.getPrimaryPrincipal();//得到唯一的安全数据

        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        //2.根据用户数据获取用户的权限信息(所有角色,所有权限)
        /*Set<String> roles =  userService.findRnamesByUserName(user.getUsername());
        if(!CollectionUtils.isEmpty(roles)){
            for (String role : roles) {
                System.out.println("角色名称："+role);
                Set<String> perssion = roleService.findByRolesName(role);
                for (String name : perssion) {

                    info.addRole(role);
                    info.addStringPermission(name);
                    System.out.println("拥有权限:-------------"+name);
                }
            }
        }*/
        Set<String> roles = new HashSet<>();
        Set<String> perms = new HashSet<>();
        user =userService.findByUsernameAndPassword(user.getUsername(),user.getPassword());
        //如果是超级用户则添加所有权限
        if(user.getUsername().equals("maisidun")){
            try {
                List<Permission> permissionList = permissionService.findAll(null);
                //循环权限,添加权限
                for (Permission permission : permissionList) {
                    if( permission.getType().equals("3")){
                        Map<String, Object> objectMap = permissionService.findById(permission.getId());
                        perms.add((String) objectMap.get("code"));
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            for (Role role : user.getRoles()){
                roles.add(role.getName());
                System.out.println("角色名称"+role.getName());
                for (Permission permission : role.getPermissions()){
                    if(permission.getType().equals("3")){
                        System.out.println("拥有权限:"+permission.getCode());
                        perms.add(permission.getCode());
                    }

                }
            }
        }




        info.setStringPermissions(perms);
        info.setRoles(roles);
        return info;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {


        //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        //1.获取用户信息
        //String name = authenticationToken.getPrincipal().toString();
        //  得到用户名
        UsernamePasswordToken upToken = (UsernamePasswordToken)authenticationToken;
        String username = upToken.getUsername();
        String password=new String(upToken.getPassword());
        String mdPassword = Encrypt.md5(password, username);
        //2.需要补充   upToken.
        User user = userService.findByUsername(username);
        if (user != null && user.getPassword().equals(mdPassword)){
            //安全数据 ，密码，realm域名
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
            return simpleAuthenticationInfo;
        }

        return null;

    }
}
