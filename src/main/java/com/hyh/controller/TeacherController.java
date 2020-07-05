/**
 * 作者:HYH
 * 功能:前台页面控制器
 * 时间:2020年3月18日
 */
package com.hyh.controller;

import com.maxwisdom.parallel.dao.FilePropertiesDao;
import com.maxwisdom.parallel.dao.TeacherDao;
import com.maxwisdom.parallel.entity.FileProperties;
import com.maxwisdom.parallel.entity.Teacher;
import com.maxwisdom.parallel.entity.User;
import com.maxwisdom.parallel.service.FileService;
import com.maxwisdom.parallel.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author HYH
 * ConfigurationProperties:使用注解获取application.yml中的
 * 属性,person为配置文件中的对象名称,自动对下面的name变量进行赋值,可以不写@Value("${name}")
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private FilePropertiesDao filedao;
    @Autowired
    private FileService fileService;
    @Autowired
    private TeacherDao teacherDao;


    /*返回报招聘页面表单*/
    @RequestMapping("/teacherback")
    public String home(HttpServletResponse response, HttpSession session, User user) {

        return "back/teacher";
    }

    @RequestMapping("/teacheDate")
    @ResponseBody
    public String teacheDate(Teacher teacher, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");//允许所有来源访问
        response.addHeader("Access-Control-Allow-Method:POST,GET", "*");//允许访问的方式
        System.out.println("外教管理后台teacherAdd数据返回");

        return teacherService.teacherSelect(teacher);
    }



    @RequestMapping("/teacherAdd")
    @ResponseBody
    public String teacherAdd(Teacher teacher, HttpServletResponse response, HttpSession session,
                             @RequestParam(value = "photo_yourself_from", required = false) MultipartFile photo_yourself,
                             @RequestParam(value = "personal_video", required = false) MultipartFile personal_video) {
        System.out.println("te" + teacher);
        Teacher teacherlast=teacher;
        if(teacher.getTeacherdId()!=null){//更新或新加
          teacherlast= teacherService.findByTeacherdId(teacher.getTeacherdId());
            teacherlast.setTeacherName(teacher.getTeacherName());
            teacherlast.setText(teacher.getText());
            teacherlast.setNationality(teacher.getNationality());
            teacherlast.setSchool(teacher.getSchool());
        }
        if (photo_yourself != null) {
            if(teacherlast.getPicture()!=null){//删除原来的图片
                fileService.filedelete(teacherlast.getPicture().getPath());
            }

            FileProperties photo_yourselfre = fileService.fileUpload(photo_yourself);//上传成功后判断返回结果存入数据库
            if (photo_yourselfre.getUploadstatus().equals("success")) {
                teacherlast.setPicture(photo_yourselfre);
            }
        }
        if (personal_video != null) {
            if(teacherlast.getVideo()!=null){//删除原来的图片
                fileService.filedelete(teacherlast.getVideo().getPath());
            }
            FileProperties Personal_videos = fileService.fileUpload(personal_video);//上传成功后判断返回结果存入数据库
            if (Personal_videos.getUploadstatus().equals("success")) {
                teacherlast.setVideo(Personal_videos);
            }
        }
        teacherlast.setCreatedtime(new Date());
        teacherDao.save(teacherlast);
        System.out.println("添加成功");
        return "sucees";
    }


    /*外教管理删除数据*/
    @RequestMapping("/teacher_delete")
    @ResponseBody
    public String recruitment_lecturer_delete(HttpServletResponse response, HttpSession session,
                                              @RequestParam(value = "IDArray[]") Integer[] IDArray
            /*  @RequestParam(value = "delete_id", required = false, defaultValue = "") String IDArray[]*/
    ) {
        System.out.println("招聘老师删除数据");
        // String[] IDArray = delete_id.split(",");
        return teacherService.teacher_delete(IDArray);

    }

    /*外教管理修改是否展示*/
    @RequestMapping("/updateShowTeacher")
    @ResponseBody
    public String updateShowTeacher(Teacher teacher, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");//允许所有来源访问
        response.addHeader("Access-Control-Allow-Method:POST,GET", "*");//允许访问的方式
        System.out.println("招聘老师后台数据返回");

        return teacherService.updateShowTeacher(teacher);
    }

}