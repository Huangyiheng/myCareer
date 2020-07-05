package com.hyh.controller;

import com.maxwisdom.parallel.entity.Joins;
import com.maxwisdom.parallel.service.SchoolRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RequestMapping("/joins")
@Controller
public class jionsControllel {

    @Autowired
    private SchoolRegistrationService schoolRegistrationService;

    @RequestMapping("/school")
    public String jions() {
        System.out.println("学校入驻管理管理页");
        return "back/recruitment_joins";
    }


    @RequestMapping("/jions_data")
    @ResponseBody
    public String jionsData(Joins joins, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");//允许所有来源访问
        response.addHeader("Access-Control-Allow-Method:POST,GET", "*");//允许访问的方式
        System.out.println("招聘学校入驻后台数据返回");
        return schoolRegistrationService.jionsData(joins, response);
    }

    @RequestMapping("/getRecruitmentbyid")
    @ResponseBody
    public String getRecruitmentbyid(HttpServletResponse response, HttpSession session,
                                     @RequestParam(value = "id") long id
    ) {
        System.out.println("id------"+id);
        Joins joins = schoolRegistrationService.getRecruitmentbyid(id);
        System.out.println("----------"+joins);
        return "success";
    }


    /*校区加盟删除数据*/
    @RequestMapping("/joins_delete")
    @ResponseBody
    public String joins_delete(HttpServletResponse response, HttpSession session,
                                 @RequestParam(value = "IDArray[]") Integer[] IDArray
            /*  @RequestParam(value = "delete_id", required = false, defaultValue = "") String IDArray[]*/
    ) {
        // String[] IDArray = delete_id.split(",");
        System.out.println(IDArray);
        return schoolRegistrationService.joins_delete(IDArray);
    }


    /*校区加盟是否已读*/
    @RequestMapping("/updateShowJoins")
    @ResponseBody
    public String updateShowJoins(Joins joins, HttpServletResponse response) {
        return schoolRegistrationService.updateShowJoins(joins);
    }
}
