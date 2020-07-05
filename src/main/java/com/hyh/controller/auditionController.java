package com.hyh.controller;

import com.maxwisdom.parallel.entity.Joins;
import com.maxwisdom.parallel.entity.StudentRegistration;
import com.maxwisdom.parallel.service.SchoolRegistrationService;
import com.maxwisdom.parallel.service.StudentRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
@RequestMapping("/audition")
public class auditionController {
    @Autowired
    private StudentRegistrationService studentRegistrationService;

    @Autowired
    private SchoolRegistrationService schoolRegistrationService;


    @RequestMapping(value = "/receive", produces = {"text/html;charset=UTF-8;", "application/json;"})
    @ResponseBody
    public String receive(HttpServletResponse response,
                                @RequestParam(value = "age", required = false, defaultValue = "") String age,
                                @RequestParam(value = "name", required = false, defaultValue = "") String name,
                                @RequestParam(value = "phone", required = false, defaultValue = "") String phone

    ) {
        response.addHeader("Access-Control-Allow-Origin", "*");//允许所有来源访问
        response.addHeader("Access-Control-Allow-Method:POST,GET", "*");//允许访问的方式
        try {
            if(age.equals("")&&name.equals("")&&phone.equals("")){
                return "false";
            }
            StudentRegistration sr = new StudentRegistration();
            sr.setAge(age);
            sr.setCampus("");
            sr.setCourse("");
            sr.setGender("");
            sr.setName(name);
            sr.setPhone(phone);
            sr.setCreatetime(new Date());
            studentRegistrationService.save(sr);
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
        return "sucees";
    }













    @RequestMapping(value = "/apply", produces = {"text/html;charset=UTF-8;", "application/json;"})
    @ResponseBody
    public String datatableData(HttpServletResponse response,
                                @RequestParam(value = "babyage", required = false, defaultValue = "") String babyage,
                                @RequestParam(value = "babyname", required = false, defaultValue = "") String babyname,
                                @RequestParam(value = "phoneNumber", required = false, defaultValue = "") String phoneNumber

    ) {
        response.addHeader("Access-Control-Allow-Origin", "*");//允许所有来源访问
        response.addHeader("Access-Control-Allow-Method:POST,GET", "*");//允许访问的方式
        try {
            if(babyage.equals("")&&babyname.equals("")&&phoneNumber.equals("")){
                return "false";
            }
            StudentRegistration sr = new StudentRegistration();
            sr.setAge(babyage);
            sr.setCampus("");
            sr.setCourse("");
            sr.setGender("");
            sr.setName(babyname);
            sr.setPhone(phoneNumber);
            sr.setCreatetime(new Date());
            studentRegistrationService.save(sr);
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
        return "sucees";
    }


    @RequestMapping(value = "/school", produces = {"text/html;charset=UTF-8;", "application/json;"})
    @ResponseBody
    public String school(HttpServletResponse response,
                                @RequestParam(value = "schoolbabyname", required = false, defaultValue = "") String schoolbabyname,
                                @RequestParam(value = "schooladdress", required = false, defaultValue = "") String schooladdress,
                                @RequestParam(value = "schoolphone", required = false, defaultValue = "") String schoolphone,
                         @RequestParam(value = "schoolphoneNumber", required = false, defaultValue = "") String schoolphoneNumber,
                          @RequestParam(value = "schoolnum", required = false, defaultValue = "") String schoolnum,
                                     @RequestParam(value = "schoolage", required = false, defaultValue = "") String schoolage

    ) {
        response.addHeader("Access-Control-Allow-Origin", "*");//允许所有来源访问
        response.addHeader("Access-Control-Allow-Method:POST,GET", "*");//允许访问的方式
        try {
            if(schoolbabyname.equals("")&&schooladdress.equals("")&&schoolphone.equals("")&&schoolphoneNumber.equals("")&&schoolnum.equals("")&&schoolage.equals("")){
                return "false";
            }
            Joins joins = new Joins();
            joins.setName(schoolbabyname);
            joins.setAddresss(schooladdress);
            joins.setSchoolphone(schoolphone);
            joins.setPhone(schoolphoneNumber);
            joins.setSchoolnum(schoolnum);
            joins.setAge(schoolage);
            joins.setCampus("");
            joins.setRemarks("");
            joins.setRelation("");
            joins.setCreatetime(new Date());
            joins.setUpdatetime(new Date());
            schoolRegistrationService.save(joins);
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
        return "sucees";
    }




}
