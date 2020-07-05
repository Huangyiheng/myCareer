package com.hyh.controller;

import com.maxwisdom.parallel.entity.User;
import com.maxwisdom.parallel.utility.UtilityHyh;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @RequestMapping("/")
    public String home(HttpServletRequest request) {
        System.out.println("进入平行外教中文首页");
        if (UtilityHyh.JudgeIsMoblie(request)) {
            //手机版
            return "phone/pingxingwaijiao_mobile";
        } else {
            //电脑版
            return "front/pingxingwaijiao";
        }
    }


    @RequestMapping("/home1")
    public String home1(HttpServletRequest request) {
        System.out.println("进入平行外教中文首页");
        if (UtilityHyh.JudgeIsMoblie(request)) {
            //手机版
            return "phone/pingxingwaijiao_mobile";
        } else {
            //电脑版
            return "front/pingxingwaijiao";
        }
    }

    @RequestMapping("/home2")
    public String home2(HttpServletRequest request) {
        System.out.println("进入平行外教中文首页");
        if (UtilityHyh.JudgeIsMoblie(request)) {
            //手机版
            return "phone/pingxingwaijiao_mobile";
        } else {
            //电脑版
            return "front/pingxingwaijiao";
        }
    }

    @RequestMapping("/home3")
    public String home3(HttpServletRequest request) {
        System.out.println("进入平行外教中文首页");
        if (UtilityHyh.JudgeIsMoblie(request)) {
            //手机版
            return "phone/pingxingwaijiao_mobile";
        } else {
            //电脑版
            return "front/pingxingwaijiao";
        }
    }

    @RequestMapping("/home4")
    public String home4(HttpServletRequest request) {
        System.out.println("进入平行外教中文首页");
        if (UtilityHyh.JudgeIsMoblie(request)) {
            //手机版
            return "phone/pingxingwaijiao_mobile";
        } else {
            //电脑版
            return "front/pingxingwaijiao";
        }
    }

    @RequestMapping("/login")
    public String loginPage() {
        System.out.println("进入登录页面");

        return "back/userlogin";
    }

    @RequestMapping("/chooseTeacher1")
    public String teacher1(Model model, HttpSession session, HttpServletRequest request) {
        System.out.println("进入外教支持首页");
        if (UtilityHyh.JudgeIsMoblie(request)) {
            //手机版
            return "phone/choose_fteaher_moblie";
        } else {
            //电脑版
            return "front/choose_fteaher";
        }
    }

    @RequestMapping("/chooseTeacher2")
    public String teacher2(Model model, HttpSession session, HttpServletRequest request) {
        System.out.println("进入外教支持首页");
        if (UtilityHyh.JudgeIsMoblie(request)) {
            //手机版
            return "phone/choose_fteaher_moblie";
        } else {
            //电脑版
            return "front/choose_fteaher";
        }
    }

    @RequestMapping("/chooseTeacher3")
    public String teacher3(Model model, HttpSession session, HttpServletRequest request) {
        System.out.println("进入外教支持首页");
        if (UtilityHyh.JudgeIsMoblie(request)) {
            //手机版
            return "phone/choose_fteaher_moblie";
        } else {
            //电脑版
            return "front/choose_fteaher";
        }
    }


    @RequestMapping("/user")
    @ResponseBody
    public String login(User user) {
        System.out.println("方法执行");
        //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();

        //当前是否认证
        if (!subject.isAuthenticated()) {
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                    user.getUsername(),
                    user.getPassword()
            );
            System.out.println("姓名" + user.getUsername());
            System.out.println("密码" + user.getPassword());
            try {
                //进行验证，这里可以捕获异常，然后返回对应信息
                subject.login(usernamePasswordToken);
//            subject.checkRole("admin");
//            subject.checkPermissions("query", "add");
            } catch (AuthenticationException e) {
                e.printStackTrace();
                return "账号或密码错误！";
            } catch (AuthorizationException e) {
                e.printStackTrace();
                return "没有权限";
            }
            System.out.println("执行sucees");
            return "sucees";
        }
        System.out.println("error执行");
        return "服务器异常";
    }


    //注解验角色和权限
    @RequiresRoles("admin")
    @RequiresPermissions("add")
    @RequestMapping("/index")
    public String index() {
        return "index!";
    }

    //退出
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {

        System.out.println("退出方法");
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.setAttribute("loginuser",null);//清空session
        return "back/userlogin";

    }
    //没有权限
    @RequestMapping("/no_access")
    public String no_access(HttpServletRequest request) {
        return "back/no_access";
    }

}
