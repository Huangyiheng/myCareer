/**
 * 作者:HYH
 * 功能:前台页面控制器
 * 时间:2020年3月18日
 */
package com.hyh.controller;

import com.maxwisdom.parallel.dao.StudentRegistrationDao;
import com.maxwisdom.parallel.entity.CityPartener;
import com.maxwisdom.parallel.entity.StudentRegistration;
import com.maxwisdom.parallel.entity.Teacher;
import com.maxwisdom.parallel.service.CityPartenerService;
import com.maxwisdom.parallel.service.JobWantedService;
import com.maxwisdom.parallel.service.TeacherService;
import com.maxwisdom.parallel.service.UserService;
import com.maxwisdom.parallel.utility.UtilityHyh;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author HYH
 * ConfigurationProperties:使用注解获取application.yml中的
 * 属性,person为配置文件中的对象名称,自动对下面的name变量进行赋值,可以不写@Value("${name}")
 */


@RequestMapping("/parallel")
@Controller
//@RequestMapping("/back")
//@ConfigurationProperties(prefix = "person")
public class parallelController {

    @Autowired
    private StudentRegistrationDao srdao;
    @Autowired
    private JobWantedService jobWantedService;
    @Autowired
    private UserService userService;
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CityPartenerService cityPartenerService;

    /*平行外教首页*/
    @RequestMapping("")
    public String pingxingwaijiao(HttpSession session, HttpServletRequest request) {
        System.out.println("进入平行外教中文首页");

        if (UtilityHyh.JudgeIsMoblie(request)) {
            //手机版
            return "phone/pingxingwaijiao_mobile";
        } else {
            //电脑版
            return "front/pingxingwaijiao";
        }


    }


    @RequestMapping("/aboutUs")
    public String aboutUs(HttpSession session, HttpServletRequest request) {
        System.out.println("关于我们");

        if (UtilityHyh.JudgeIsMoblie(request)) {
            //手机版
            return "phone/aboutUs_mobile";
        } else {
            //电脑版
            return "front/aboutUs";
        }

    }



    /*演示站*/
    /*演示站首页*/
    @RequestMapping("/maxwisdom")
    public String maxsidom(HttpSession session,HttpServletRequest request) {
        System.out.println("演示站首页");


        if (UtilityHyh.JudgeIsMoblie(request)) {
            //手机版
            return "phone/maxwisdom_mobile";
        } else {
            //电脑版
            return "maxwisdom/maxsidom";
        }
    }


    /**
     * 城市合伙人
     */
    @RequestMapping("/partener")
    public String partener(HttpSession session,HttpServletRequest request) {
        System.out.println("进入城市合伙人首页");

        if (UtilityHyh.JudgeIsMoblie(request)) {
            //手机版
            return "phone/partener_moblie";
        } else {
            //电脑版
            return "front/partener";
        }
    }





    /**
     * 查看外教
     *
     * @return
     */
    @RequestMapping("/chooseTeacher")
    public String teacher(Model model,HttpSession session,HttpServletRequest request) {
        System.out.println("进入外教支持首页");
        if (UtilityHyh.JudgeIsMoblie(request)) {
            //手机版
            return "phone/choose_fteaher_moblie";
        } else {
            //电脑版
            return "front/choose_fteaher";
        }
    }





    /**
     * commodityOnePxwj
     * 进入绘本网站图首页
     */
    @RequestMapping("/commodityOne")
    public String commodityOnePxwj(HttpSession session,HttpServletRequest request) {
        System.out.println("进入自拼网站首页");
        if (UtilityHyh.JudgeIsMoblie(request)) {
            //手机版
            return "phone/commodityOnePxwj_moblie";
        } else {
            //电脑版
            return "front/commodityOnePxwj";
        }
    }

    /**
     * drawTheBookWebsite
     * 进入绘本网站图首页
     */
    @RequestMapping("/drawTheBookWebsite")
    public String drawTheBookWebsite(HttpSession session,HttpServletRequest request) {
        System.out.println("进入绘本网站图首页");
        if (UtilityHyh.JudgeIsMoblie(request)) {
            //手机版
            return "phone/drawTheBookWebsite_moblie";
        } else {
            //电脑版
            return "front/drawTheBookWebsite";
        }
    }


    /**
     * websiteSystemCourse
     * 进入网站体系课首页
     */
    @RequestMapping("/websiteSystemCourse")
    public String websiteSystemCourse(HttpSession session,HttpServletRequest request) {
        System.out.println("进入网站体系课首页");

        if (UtilityHyh.JudgeIsMoblie(request)) {
            //手机版
            return "phone/websiteSystemCourse_moblie";
        } else {
            //电脑版
            return "front/websiteSystemCourse";
        }
    }


    /**
     * websiteEnlightenment
     * 网站启蒙课
     */
    @RequestMapping("/websiteEnlightenment")
    public String websiteEnlightenment(HttpSession session,HttpServletRequest request) {
        System.out.println("进入网站启蒙课首页");
        if (UtilityHyh.JudgeIsMoblie(request)) {
            //手机版
            return "phone/websiteEnlightenment_moblie";
        } else {
            //电脑版
            return "front/websiteEnlightenment";
        }
    }

    /**
     * 线上线下同步学
     *
     * @param session
     * @return
     */

    @RequestMapping("/synchronousLearning")
    public String synchronousLearning(HttpSession session,HttpServletRequest request) {
        System.out.println("线上线下同步学");

        if (UtilityHyh.JudgeIsMoblie(request)) {
            //手机版
            return "phone/synchronousLearning_moblie";
        } else {
            //电脑版
            return "front/synchronousLearning";
        }

    }


    /**
     * 双师
     *
     * @param session
     * @return
     */

    @RequestMapping("/doubleClass")
    public String doubleClass(HttpSession session,HttpServletRequest request) {
        System.out.println("进入双师首页");

        if (UtilityHyh.JudgeIsMoblie(request)) {
            //手机版
            return "phone/doubleClass_moblie";
        } else {
            //电脑版
            return "front/doubleClass";
        }
    }
    /*, @PathVariable("id") int id*/
    @RequestMapping("/teacherDetail/{id}")
    public String teacherDetail(HttpSession session, Model model, @PathVariable("id") int id, HttpServletRequest request) {
        System.out.println("详情页");
        Teacher teacher = teacherService.findByTeacherdId(id);

        model.addAttribute("viedo", teacher.getVideo().getFileId());
        model.addAttribute("jobid", teacher.getTeacherdId());
        model.addAttribute("nationality", teacher.getNationality());
        model.addAttribute("kimg", "dianaK.png");
        model.addAttribute("zimg", teacher.getPicture().getFileId());
        model.addAttribute("name", teacher.getTeacherName());
        model.addAttribute("date", "Sept 2013-May 2016");
        model.addAttribute("mioashu", teacher.getText());
        model.addAttribute("xiangq",  teacher.getText());
        model.addAttribute("bimg", "b1.png");
        if (UtilityHyh.JudgeIsMoblie(request)) {
            //手机版
            return "phone/Choose_detail_moblie";
        } else {
            //电脑版
            return "front/choose_Detail";
        }
    }




    /*城市入驻立即提交*/
    @RequestMapping(value = "/cityPartener", produces = {"text/html;charset=UTF-8;", "application/json;"})
    @ResponseBody
    public String cityPartener(HttpServletResponse response,
                                @RequestParam(value = "name", required = false, defaultValue = "") String name,
                                @RequestParam(value = "gender", required = false, defaultValue = "") String gender,
                                @RequestParam(value = "age", required = false, defaultValue = "") String age,
                               @RequestParam(value = "phone", required = false, defaultValue = "") String phone,
                               @RequestParam(value = "address", required = false, defaultValue = "") String address

    ) {
        response.addHeader("Access-Control-Allow-Origin", "*");//允许所有来源访问
        response.addHeader("Access-Control-Allow-Method:POST,GET", "*");//允许访问的方式
        try {
            if (name.equals("") && gender.equals("") && age.equals("")&& phone.equals("")&& address.equals("")) {
                return "false";
            }
            CityPartener cp = new CityPartener();
            cp.setName(name);
            cp.setGender(gender);
            cp.setAge(age);
            cp.setPhone(phone);
            cp.setAddress(address);
            cp.setCreatetime(new Date());
            cityPartenerService.save(cp);
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
        return "sucees";
    }

    /*平行外教学生报名提交*/
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
            if (babyage.equals("") && babyname.equals("") && phoneNumber.equals("")) {
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
            srdao.save(sr);
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
        return "sucees";
    }

}