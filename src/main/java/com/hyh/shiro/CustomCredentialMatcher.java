package com.hyh.shiro;

import com.maxwisdom.parallel.utility.Encrypt;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * 密码自定义比较器
 */
public class CustomCredentialMatcher extends SimpleCredentialsMatcher {
    /* 执行密码比较
    * 第一个参数：将来传进来的对象，代表用户在界面上输入的用户名和密码信息
    * 第二个参数：代表用户在数据库的信息，加密后的密码
    * AuthenticationInfo 接口中的 getCredentials()方法得到的就是数据库的密码
    信息
    */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token,
                                      AuthenticationInfo info) {
    //1.把 AuthenticationToken 转成 UsernamePasswordToken 类型
        UsernamePasswordToken utoken = (UsernamePasswordToken) token;
    //2.得到用户输入的用户名和密码
        String username = utoken.getUsername();
        String password = new String(utoken.getPassword());
    //3.把用户输入的密码加密
        String md5Password = Encrypt.md5(password, username);
    //4.比较页面输入的密码和数据库的密码
        return equals(md5Password, info.getCredentials());
    }

}
