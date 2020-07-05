package com.hyh.shiro;

import com.maxwisdom.parallel.entity.Permission;
import com.maxwisdom.parallel.service.PermissionService;
import org.apache.shiro.mgt.AuthenticatingSecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Configuration
public class ShiroConfig {

    @Autowired
    private PermissionService permissionService;

    //将自己的验证方式加入容器
    @Bean
    public CustomCredentialMatcher credentialMatcher() {
        CustomCredentialMatcher matcher = new CustomCredentialMatcher();
        return matcher;
    }

    //自定义的Ralm类
    @Bean
    public UserCustomRealm myShiroRealm() {
        UserCustomRealm customRealm = new UserCustomRealm();
        //加密算法
        customRealm.setCredentialsMatcher(credentialMatcher());
        return customRealm;
    }

    //权限管理，配置主要是Realm的管理认证

    /**
     * 构建securityManager
     *
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        return securityManager;
    }

/*
        * 设置所有的过滤器,有顺序map
        * key=拦截的url地址
        * value=过滤器类型
        * anon:例子/admins/**=anon 没有参数，表示可以匿名使用。

                authc:例如/admins/user/**=authc表示需要认证(登录)才能使用，没有参数

            roles(角色)：例子/admins/user/**=roles[admin],参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，当有多个参数时，例如admins/user/**=roles["admin,guest"],每个参数通过才算通过，相当于hasAllRoles()方法。

                perms（权限）：例子/admins/user/**=perms[user:add:*],参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，例如/admins/user/**=perms["user:add:*,user:modify:*"]，当有多个参数时必须每个参数都通过才通过，想当于isPermitedAll()方法。

            rest：例子/admins/user/**=rest[user],根据请求的方法，相当于/admins/user/**=perms[user:method] ,其中method为post，get，delete等。

            port：例子/admins/user/**=port[8081],当请求的url的端口不是8081是跳转到schemal://serverName:8081?queryString,其中schmal是协议http或https等，serverName是你访问的host,8081是url配置里port的端口，queryString

                是你访问的url里的？后面的参数。

                authcBasic：例如/admins/user/**=authcBasic没有参数表示httpBasic认证

                ssl:例子/admins/user/**=ssl没有参数，表示安全的url请求，协议为https

                user:例如/admins/user/**=user没有参数表示必须存在用户，当登入操作时不做检查
        * */

    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(AuthenticatingSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> map = new LinkedHashMap<>();

        try {
            List<Permission> permissionList = permissionService.findAll(null);
            //循环权限,添加权限
            for (Permission permission : permissionList) {
               if( permission.getType().equals("3")){

                   Map<String, Object> objectMap = permissionService.findById(permission.getId());
                   if(objectMap.get("apiLevel").equals("2")){

                       map.put((String) objectMap.get("apiUrl"), "perms[" + (String) objectMap.get("code") + "]");
                   }else{
                       map.put((String) objectMap.get("apiUrl"), "anon");
                   }
               }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        //登出


        //map.put("/wewew/**", "anon");
        //  map.put("/sys/**","roles[admin]");


        map.put("/parallel/**", "anon");
        map.put("/user/**", "anon");
        map.put("/job/**", "anon");
        map.put("/timetable/**", "anon");
        map.put("/sendmail/**", "anon");
        map.put("/recruitstudent/**", "anon");
        map.put("/audition/**", "anon");
        map.put("/registration/**", "anon");
        map.put("/file/**", "anon");
        map.put("/joins/**", "anon");
        map.put("/", "anon");
        map.put("/home1", "anon");
        map.put("/home2", "anon");
        map.put("/home3", "anon");
        map.put("/home4", "anon");
        map.put("/chooseTeacher1", "anon");
        map.put("/chooseTeacher2", "anon");
        map.put("/chooseTeacher3", "anon");
        map.put("/logout", "logout");
        map.put("/static/**", "anon");
//对所有用户认证
        map.put("/**", "authc");


        //登录    没有认证的进入页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        //首页    认证成功进入的页面
        shiroFilterFactoryBean.setSuccessUrl("/");
        //错误页面，认证不通过跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/no_access");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        return shiroFilterFactoryBean;
    }

    //加入注解的使用，不加入这个注解不生效
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(AuthenticatingSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


    //不加这个注解不生效，具体不详
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }

}
