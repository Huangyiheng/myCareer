package com.hyh.controller;

import com.maxwisdom.parallel.entity.AboutUs;
import com.maxwisdom.parallel.entity.SalesCalls;
import com.maxwisdom.parallel.entity.User;
import com.maxwisdom.parallel.service.SalesCallsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/salescalls")
public class SalesCallsControllel {
    @Autowired
    private SalesCallsService salesCallsService;

    @RequestMapping("/home")
    public String home(AboutUs aboutUs, HttpServletResponse response) {
        System.out.println("进入试听预约");

        return "back/salesCalls";
    }

    @RequestMapping("/salescallsDate")
    @ResponseBody
    public String salescallsDate(SalesCalls salesCalls, HttpServletResponse response,
                                 @RequestParam(value = "userid",required = false) Long userid
    ) {
        System.out.println("返回试听预约数据");

        response.addHeader("Access-Control-Allow-Origin", "*");//允许所有来源访问
        response.addHeader("Access-Control-Allow-Method:POST,GET", "*");//允许访问的方式
        if(userid!=null){


        User user = new User();
        user.setId(userid);
        salesCalls.setUser(user);
        }
        String data = salesCallsService.getsalescallsDate(salesCalls);
        System.out.println(data);

        return data;
    }

    @RequestMapping("/salescallsAdd")
    @ResponseBody
    public String salescallsAdd(SalesCalls salesCalls, HttpServletResponse response

    ) {
        System.out.println("返回试听预约数据");

        response.addHeader("Access-Control-Allow-Origin", "*");//允许所有来源访问
        response.addHeader("Access-Control-Allow-Method:POST,GET", "*");//允许访问的方式
        String data = salesCallsService.salescallsAdd(salesCalls);
        System.out.println(data);

        return data;
    }

    @RequestMapping("/salescallsdelete")
    @ResponseBody
    public String salescallsdelete(HttpServletResponse response, HttpSession session, @RequestParam(value = "IDArray[]") Integer[] IDArray) {
        System.out.println("返回试听预约数据");

        response.addHeader("Access-Control-Allow-Origin", "*");//允许所有来源访问
        response.addHeader("Access-Control-Allow-Method:POST,GET", "*");//允许访问的方式
        String data = salesCallsService.salescallsdelete(IDArray);
        System.out.println(data);

        return data;
    }

    @RequestMapping("/importexcl")
    @ResponseBody
    public String importexcl(HttpServletResponse response, HttpSession session
            , @RequestParam(value = "importexcl", required = false) MultipartFile importexcl) {
        System.out.println("导入数据");
        response.addHeader("Access-Control-Allow-Origin", "*");//允许所有来源访问
        response.addHeader("Access-Control-Allow-Method:POST,GET", "*");//允许访问的方式

        return salesCallsService.importexcl(importexcl);
    }

    /*分配老师数据*/
    @RequestMapping("/allocation")
    @ResponseBody
    public String allocation(HttpServletResponse response, HttpSession session
            , @RequestParam(value = "IDArray[]") Integer[] IDArray
            , @RequestParam(value = "user") Long user
    ) {
        System.out.println("返回试听预约数据");
        response.addHeader("Access-Control-Allow-Origin", "*");//允许所有来源访问
        response.addHeader("Access-Control-Allow-Method:POST,GET", "*");//允许访问的方式
        String data = salesCallsService.allocation(IDArray, user);

        return data;
    }

}
