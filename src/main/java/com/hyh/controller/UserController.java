package com.hyh.controller;

import com.alibaba.fastjson.JSON;
import com.maxwisdom.parallel.entity.User;
import com.maxwisdom.parallel.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户管理页面
 */
@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;


    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * 添加超级管理员
     *
     * @return
     */
    @RequestMapping("/admin")
    public String addUser() {
        try {
            User user = new User();
            user.setUsername("maisidun");
            user.setPassword("maisidun");
            user.setName("开发者");
            userService.addUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "back/userlogin";
    }

    @PostMapping("/jobadduser")
    @ResponseBody
    public String jobadduser(User user) {
        try {
            userService.addUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "sucees";
    }


    /**
     * 添加用户信息
     *
     * @param user
     * @param checkid
     * @param response
     * @param session
     * @return
     */

    @RequestMapping("/useradd")
    @ResponseBody
    public String useradd(User user, @RequestParam(value = "checkid", required = false) String checkid,
                          @RequestParam(value = "avatar_yourself", required = false) MultipartFile avatar_yourself,
                          HttpServletResponse response, HttpSession session
    ) {
        try {

            String userDb = userService.useradd(user, checkid, avatar_yourself);
            if (userDb == null) {
                return "error";
            }
            return "sucees";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @RequestMapping("/updateName")
    @ResponseBody
    public String updateName(
            @RequestParam(value = "userid", required = false) String userId,
            @RequestParam(value = "username", required = false) String username
    ) {
        User usernameDb = userService.findByUsername(username);
        if (userId == null) {
            if (usernameDb == null) {
                return "{\"valid\":true}";
            } else {
                return "{\"valid\":false}";
            }
        } else {
            return "{\"valid\":true}";
        }
    }

    /**
     * 查询所有的用户信息
     */

    @RequestMapping("/deleteUser")
    @ResponseBody
    public String deleteUserId(HttpServletResponse response, HttpSession session,
                               @RequestParam(value = "IDArray[]") Long[] IDArray) {
        Map<String, Map> filemaperror = new HashMap<>();
        if (IDArray != null) {
            for (Long id : IDArray) {
                userService.deleteUserId(id);
            }
        }
        return "success";
    }

    /*根据id获取用户*/
    @RequestMapping("/getuserbyid")
    @ResponseBody
    public String getuserbyid(User user, HttpServletResponse response, HttpSession session) {
        return JSON.toJSONString(userService.findById(user.getId()));

    }


    /*返回role节点*/
    @RequestMapping("/usertreedata")
    @ResponseBody
    public String usertreedata(HttpServletResponse response, HttpSession session,
                               @RequestParam(value = "userid", required = false, defaultValue = "0") Long userid
    ) {

        return userService.getUserTreeListJson(userid);
    }

    /*用户删除数据*/
    @RequestMapping("/user_delete")
    @ResponseBody
    public String user_delete(HttpServletResponse response, HttpSession session,
                              @RequestParam(value = "IDArray[]") Integer[] IDArray
    ) {
        // String[] IDArray = delete_id.split(",");
        return userService.user_delete(IDArray);
    }

    @RequestMapping(value = "/user_data", produces = {"text/html;charset=UTF-8;", "application/json;"})
    @ResponseBody
    public String admin_userdata(User aduser, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");//允许所有来源访问
        response.addHeader("Access-Control-Allow-Method:POST,GET", "*");//允许访问的方式
        String data = userService.getuserdata(aduser);

        System.out.println(data);
        return data;
    }

    /*获取所有顾问老师*/
    @PostMapping("/counseloruser")
    @ResponseBody
    public String counseloruser() {
        try {
            List<User> userList = userService.findAllUser();
            return JSON.toJSONString(userList);

        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

    }
}
