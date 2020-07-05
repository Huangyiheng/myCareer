/**
 * 作者:HYH
 * 功能:前台页面控制器
 * 时间:2020年3月18日
 */
package com.hyh.controller;

import com.maxwisdom.parallel.entity.User;
import com.maxwisdom.parallel.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author HYH
 * ConfigurationProperties:使用注解获取application.yml中的
 * 属性,person为配置文件中的对象名称,自动对下面的name变量进行赋值,可以不写@Value("${name}")
 */
@RequestMapping("/back")
@Controller
//@RequestMapping("/back")
//@ConfigurationProperties(prefix = "person")
public class BackController {
    @Autowired
    private UserService userService;


    /*默认跳转到后台首页*/
    @RequestMapping("")
    public String maxwisdombackhomedf(HttpSession session, HttpServletRequest request, Model model) {

        System.out.println("进入后台首页");
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        user= userService.findByUsernameAndPassword(user.getUsername(),user.getPassword());
        model.addAttribute("user",user);

        Map<String, Object> res = new HashMap<String, Object>();
        res.put("ip:", request.getScheme() + "://" +request.getServerName()
                + ":" + request.getServerPort() + request.getRequestURI());
        System.out.println(res);


        return "back/home";
    }


    @RequestMapping("/user_home")
    public String admin_user() {
        System.out.println("用户管理页");
        return "back/user";
    }





}
