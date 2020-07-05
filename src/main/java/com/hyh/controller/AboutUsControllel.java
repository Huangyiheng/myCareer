package com.hyh.controller;

import com.alibaba.fastjson.JSON;
import com.maxwisdom.parallel.dao.AboutUsDao;
import com.maxwisdom.parallel.dao.FilePropertiesDao;
import com.maxwisdom.parallel.entity.AboutUs;
import com.maxwisdom.parallel.entity.FileProperties;
import com.maxwisdom.parallel.service.AboutUsService;
import com.maxwisdom.parallel.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("/aboutUs")
public class AboutUsControllel {

    @Autowired
    private FileService fileService;

    @Autowired
    private AboutUsDao aboutUsDao;

    @Autowired
    private AboutUsService aboutUsService;

    @Autowired
    private FilePropertiesDao filePropertiesDao;
    /*返回后台管理页面*/
    @RequestMapping("/aboutUs")
    public String aboutUs() {
        return "back/aboutUs";
    }

    @RequestMapping("/aboutUsDate")
    @ResponseBody
    public String aboutUsDate(AboutUs aboutUs, HttpServletResponse response) {
        System.out.println("关于我们后台数据返回");
        return aboutUsService.aboutUsSelect(aboutUs);
    }


    @RequestMapping("/aboutUsAdd")
    @ResponseBody
    public String teacherAdd(AboutUs aboutUs, HttpServletResponse response, HttpSession session,
                             @RequestParam(value = "photo_yourself", required = false) MultipartFile photo_yourself) {
        AboutUs   aboutUslast = aboutUs;
        if(aboutUs.getId()!=null){//更新或新加
            aboutUslast = aboutUsService.findById(aboutUs.getId());
            aboutUslast.setContent(aboutUs.getContent());
            aboutUslast.setCreateTime(new Date());
            aboutUslast.setShowAboutUs(1);
        }
        if (photo_yourself != null) {
            if(aboutUslast.getP_yourself()!=null){//删除原来的图片
                fileService.filedelete(aboutUslast.getP_yourself().getPath());
            }

            FileProperties photo_yourselfre = fileService.fileUpload(photo_yourself);//上传成功后判断返回结果存入数据库
            if (photo_yourselfre.getUploadstatus().equals("success")) {
                aboutUslast.setP_yourself(photo_yourselfre);
            }
        }
        aboutUslast.setCreateTime(new Date());
        aboutUslast.setShowAboutUs(1);
        aboutUsDao.save(aboutUslast);
        System.out.println("添加成功");
        return "sucees";
    }

    //    updateShowAbuotUs
    @RequestMapping("/updateShowAbuotUs")
    @ResponseBody
    public String updateShowAbuotUs(AboutUs aboutUs, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");//允许所有来源访问
        response.addHeader("Access-Control-Allow-Method:POST,GET", "*");//允许访问的方式
        System.out.println("关于我们后台返回");

        return aboutUsService.updateShowAbuotUs(aboutUs);
    }



    /*关于我们删除数据*/
    @RequestMapping("/aboutUs_delete")
    @ResponseBody
    public String aboutUs_delete(HttpServletResponse response, HttpSession session,
                                              @RequestParam(value = "IDArray[]") Integer[] IDArray
            /*  @RequestParam(value = "delete_id", required = false, defaultValue = "") String IDArray[]*/
    ) {
        // String[] IDArray = delete_id.split(",");
        return aboutUsService.aboutUs_delete(IDArray);
    }

    /*根据id获取内容*/
    @RequestMapping("/getaboutUsbyid")
    @ResponseBody
    public String getaboutUsbyid(AboutUs aboutUs, HttpServletResponse response, HttpSession session) {

        return JSON.toJSONString(aboutUsService.findById(aboutUs.getId()));

    }

}
