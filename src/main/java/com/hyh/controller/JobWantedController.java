/**
 * 作者:HYH
 * 功能:前台页面控制器
 * 时间:2020年3月18日
 */
package com.hyh.controller;

import com.alibaba.fastjson.JSONArray;
import com.maxwisdom.parallel.dao.FilePropertiesDao;
import com.maxwisdom.parallel.dao.JobWantedDao;
import com.maxwisdom.parallel.dao.UsersDao;
import com.maxwisdom.parallel.entity.*;
import com.maxwisdom.parallel.service.AboutUsService;
import com.maxwisdom.parallel.service.FileService;
import com.maxwisdom.parallel.service.JobWantedService;
import com.maxwisdom.parallel.service.TeacherService;
import com.maxwisdom.parallel.utility.UtilityHyh;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Date;

/**
 * @author HYH
 * ConfigurationProperties:使用注解获取application.yml中的
 * 属性,person为配置文件中的对象名称,自动对下面的name变量进行赋值,可以不写@Value("${name}")
 */
@Controller
@RequestMapping("/job")
public class JobWantedController {

    @Autowired
    private JobWantedService jobWantedService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private FilePropertiesDao filedao;
    @Autowired
    private FileService fileService;
    @Autowired
    private JobWantedDao jobwanteddao;
    @Autowired
    private UsersDao usersDao;


    @Autowired
    private AboutUsService aboutUsService;
    /*外教招聘格鲁吉亚*/
    @RequestMapping("")
    public String maxwisdombackhomedf(HttpSession session, HttpServletRequest request) {
        System.out.println("进入平行外教英文首页");
        if (UtilityHyh.JudgeIsMoblie(request)) {
            //手机版
            return "phone/job_home_moblie";
        } else {
            //电脑版
            return "front/job_home";
        }
    }

    /*返回报招聘页面表单*/
    @RequestMapping("/paraller_home_sub")
    @ResponseBody
    public String home(HttpServletResponse response, HttpSession session, JobWanted jobWanted) {
        JobWanted jobWanted1=jobWantedService.findByEMail(jobWanted.geteMail());
        if(jobWanted1==null){//如果没有此用户则创建
            jobWanted.setStep(0);//第一步填写表单页面
            jobWanted.setStepState(0);//0未审核,1通过,2未通过
            long id=  jobwanteddao.save(jobWanted).getJobwantedId();
            session.setAttribute("jobWantedId", id);
            return "registersucess";
        }else{
            if(jobWanted1.getPassword().equals(jobWanted.getPassword())){//如果密码正确
                session.setAttribute("jobWantedId", jobWanted1.getJobwantedId());
                return "logsucess";
            }else{//密码不正确
                return "passwroderror";
            }
        }



    }
    //验证邮箱是否已存在
    @RequestMapping("/emailrepeat")
    @ResponseBody
    public String emailrepeat(HttpServletResponse response,
                              @RequestParam(value = "eMail", required = false) String eMail) {
        response.addHeader("Access-Control-Allow-Origin", "*");//允许所有来源访问
        response.addHeader("Access-Control-Allow-Method:POST,GET", "*");//允许访问的方式
        System.out.println("验证邮箱是否重复");
        System.out.println(eMail);

        JobWanted jobWanted = jobWantedService.findByEMail(eMail);
        String jsonResult;
        if (jobWanted == null) {
            jsonResult = "{\"valid\":true}";//不存在
        } else {
            jsonResult = "{\"valid\":false}";
        }
        return jsonResult;
    }
    /*到表单求职页面*/
    @RequestMapping("/job_wanted")
    public String job_wanted(HttpServletRequest request, HttpServletResponse response, HttpSession session, User user) {
        //手机版
        return "front/job_wanted";
    }

    /*到表单求职页面*/
    @RequestMapping("/job_wanted_to")
    public String job_wanted_to(HttpServletRequest request, Model model, HttpServletResponse response, HttpSession session, User user) {
        JobWanted jobWanted=  jobWantedService.findByJobwantedId((long)session.getAttribute("jobWantedId"));
        model.addAttribute("Step", jobWanted.getStep());
        model.addAttribute("StepState", jobWanted.getStepState());
        model.addAttribute("Nationality", jobWanted.getNationality());
        if (UtilityHyh.JudgeIsMoblie(request)) {
            //手机版
            return "phone/job_wanted_to_moblie";
        } else {
            //电脑版
            return "front/job_wanted_to";
        }
    }


    @RequestMapping("/showTeacher")
    @ResponseBody
    public String showTeacher(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                              @RequestParam(value = "jobWanted", required = false) String jobWantedJSon
    ) {
        Teacher teacher = (Teacher) JSONArray.parseObject(jobWantedJSon, Teacher.class);
        System.out.println("外教展示请求后台");
        String jon = teacherService.findByshowTeacher(teacher);

        System.out.println(jon);
        return jon;
    }


    @RequestMapping("/showAboutUs")
    @ResponseBody
    public String showAboutUs(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                              @RequestParam(value = "aboutUs", required = false) String jobWantedJSon
    ) {
        AboutUs aboutUs = JSONArray.parseObject(jobWantedJSon,AboutUs.class);

        System.out.println("关于我们展示请求后台");

        String jon = aboutUsService.findByshowAboutUs(aboutUs);
        System.out.println(jon);
        return jon;
    }


    /*返回后台管理页面*/
    @RequestMapping("/back")
    public String back() {
        return "back/recruitment_lecturer";
    }

    @RequestMapping("/getfile")
    public void getfile(HttpServletResponse response, HttpSession session,
                        @RequestParam(value = "fileid", required = false) long fileid) {

        try {
            FileProperties picFile = filedao.findById(fileid).get();
            //读取指定路径下面的文件
            InputStream in = new FileInputStream(picFile.getPath());
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            //创建存放文件内容的数组
            byte[] buff = new byte[1024];
            //所读取的内容使用n来接收
            int n;
            //当没有读取完时,继续读取,循环
            while ((n = in.read(buff)) != -1) {
                //将字节数组的数据全部写入到输出流中
                outputStream.write(buff, 0, n);
            }
            //强制将缓存区的数据进行输出
            outputStream.flush();
            //关流
            outputStream.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @RequestMapping("/recruitment_lecturer_date")
    @ResponseBody
    public String recruitment_lecturer_date(JobWanted jobwanted, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");//允许所有来源访问
        response.addHeader("Access-Control-Allow-Method:POST,GET", "*");//允许访问的方式
        System.out.println("招聘老师后台数据返回");
        jobwanted.setDateOfBirth(new Date());
        return jobWantedService.jobwantedselect(jobwanted, response);
    }



    @RequestMapping("/updateShowTeacher")
    @ResponseBody
    public String updateShowTeacher(JobWanted jobwanted, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");//允许所有来源访问
        response.addHeader("Access-Control-Allow-Method:POST,GET", "*");//允许访问的方式
        System.out.println("招聘老师后台数据返回");

        return jobWantedService.updateShowTeacher(jobwanted);
    }

    @RequestMapping("/updatejob")
    @ResponseBody
    public String updatejob(JobWanted jobwanted, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");//允许所有来源访问
        response.addHeader("Access-Control-Allow-Method:POST,GET", "*");//允许访问的方式
        System.out.println("招聘老师后台数据返回");



        return jobWantedService.update(jobwanted);

        //  return jobWantedService.updateShowTeacher(jobwanted);
    }



    @RequestMapping("/Agree_interview")
    @ResponseBody
    public String Agree_interview(JobWanted jobwanted, HttpSession session, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");//允许所有来源访问
        response.addHeader("Access-Control-Allow-Method:POST,GET", "*");//允许访问的方式
        JobWanted jobWantednew=jobWantedService.findByJobwantedId((long)session.getAttribute("jobWantedId"));
        System.out.println("同意面试");
        jobWantednew.setStep(3);
        jobWantednew.setStepState(0);

        new Thread() {
            public void run() {
                jobWantedService.sendmailsuccess(jobWantednew);//发送状态更新邮件
            };
        }.start();
        try {
            jobwanteddao.save(jobWantednew);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }


        //  return jobWantedService.updateShowTeacher(jobwanted);
    }
    /*同意合同*/
    @RequestMapping("/contractyes")
    @ResponseBody
    public String contractyes(JobWanted jobwanted, HttpSession session, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");//允许所有来源访问
        response.addHeader("Access-Control-Allow-Method:POST,GET", "*");//允许访问的方式
        JobWanted jobWantednew=jobWantedService.findByJobwantedId((long)session.getAttribute("jobWantedId"));
        System.out.println("同意签订合同");
        jobWantednew.setStep(4);
        jobWantednew.setStepState(0);
        try {
            jobwanteddao.save(jobWantednew);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }


        //  return jobWantedService.updateShowTeacher(jobwanted);
    }


    /*进入老师求职提交*/
    @RequestMapping(value = "/recruiting", produces = {"text/html;charset=UTF-8;", "application/json;"})
    @ResponseBody
    public String recruiting(HttpServletResponse response, HttpSession session,
                             JobWanted jobwanted,
                             @RequestParam(value = "photo_yourself_from", required = false) MultipartFile photo_yourself,
                             @RequestParam(value = "personal_video_from", required = false) MultipartFile Personal_video

    ) {
        response.addHeader("Access-Control-Allow-Origin", "*");//允许所有来源访问
        response.addHeader("Access-Control-Allow-Method:POST,GET", "*");//允许访问的方式
        System.out.println("进入老师求职提交");
        JobWanted jobWantednew=jobWantedService.findByJobwantedId((long)session.getAttribute("jobWantedId"));
        jobwanted.setJobwantedId(jobWantednew.getJobwantedId());
        jobwanted.seteMail(jobWantednew.geteMail());
        jobwanted.setPassword(jobWantednew.getPassword());
        if (photo_yourself != null) {
            FileProperties photo_yourselfre = fileService.fileUpload(photo_yourself);//上传成功后判断返回结果存入数据库
            if(jobWantednew.getP_yourself()!=null){//删除原来的图片
                fileService.filedelete(jobWantednew.getP_yourself().getPath());
            }

            if (photo_yourselfre.getUploadstatus().equals("success")) {
                jobwanted.setP_yourself(photo_yourselfre);
            }
        }
        if (Personal_video != null) {
            if(jobWantednew.getPersonal_video()!=null){
                fileService.filedelete(jobWantednew.getPersonal_video().getPath());
            }

            FileProperties Personal_videos = fileService.fileUpload(Personal_video);//上传成功后判断返回结果存入数据库
            if (Personal_videos.getUploadstatus().equals("success")) {
                jobwanted.setPersonal_video(Personal_videos);
            }
        }
        jobwanted.setCreatedtime(new Date());//创建时间

        //   jobwanteddao.save(jobwanted);
        //保存求职信息
        jobwanted.setStep(1);//1填写表单,2选择课程,3面试,4签订合同
        jobwanted.setStepState(0);//0待审核,1通过,2未通过
        jobwanteddao.save(jobwanted);

        return "sucees";
    }

    /*招聘老师删除数据*/
    @RequestMapping("/recruitment_lecturer_delete")
    @ResponseBody
    public String recruitment_lecturer_delete(HttpServletResponse response, HttpSession session,
                                              @RequestParam(value = "IDArray[]") Integer[] IDArray
            /*  @RequestParam(value = "delete_id", required = false, defaultValue = "") String IDArray[]*/
    ) {
        System.out.println("招聘老师删除数据");
        // String[] IDArray = delete_id.split(",");
        return jobWantedService.recruitment_lecturer_delete(IDArray);

    }
}