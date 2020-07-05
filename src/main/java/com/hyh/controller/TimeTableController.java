/**
 * 作者:HYH
 * 功能:前台页面控制器
 * 时间:2020年3月18日
 */
package com.hyh.controller;

import com.maxwisdom.parallel.entity.JobWanted;
import com.maxwisdom.parallel.service.JobWantedService;
import com.maxwisdom.parallel.service.TimeTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author HYH
 * ConfigurationProperties:使用注解获取application.yml中的
 * 属性,person为配置文件中的对象名称,自动对下面的name变量进行赋值,可以不写@Value("${name}")
 */
@Controller
@RequestMapping("/timetable")
public class TimeTableController {
    @Autowired
    private TimeTableService timeTableService;
    @Autowired
    private JobWantedService jobWantedService;

    /*返回數據*/
  /*  @RequestMapping("/home")
    public String home() {

        return "front/timetable";
    }*/
    /*返回數據格鲁吉亚*/

    @RequestMapping("/data_Europe")
    @ResponseBody
    public String data(HttpServletResponse response, HttpSession session) {
        System.out.println("返回课程数据");
        JobWanted jobWanted=  jobWantedService.findByJobwantedId((long)session.getAttribute("jobWantedId"));


        return timeTableService.dataEurope(jobWanted);
    }
    /*返回數據加拿大*/

    @RequestMapping("/data_Nationality")
    @ResponseBody
    public String dataCanada(HttpServletResponse response, HttpSession session) {
        System.out.println("返回课程数据");
        JobWanted jobWanted=  jobWantedService.findByJobwantedId((long)session.getAttribute("jobWantedId"));

    /*    User user = new User();
        user.setId(19l);*/
        return timeTableService.dataNationality(jobWanted);
    }
/*招聘老师后台课程表*/
    @RequestMapping("/databyjob")
    @ResponseBody
    public String databyjob(HttpServletResponse response, HttpSession session,
                            @RequestParam(value = "jobid", defaultValue = "0l") long jobid
    ) {
        System.out.println("根据老师返回课程数据");
        return timeTableService.databyjob(jobid);
    }
    @RequestMapping("/databyteacher")
    @ResponseBody
    public String databyteacher(HttpServletResponse response, HttpSession session,
                            @RequestParam(value = "teacherid", defaultValue = "0l") long teacherid
    ) {
        System.out.println("根据老师返回课程数据");
        return timeTableService.databyteacher(teacherid);
    }


    //更新课程
    @RequestMapping("/dataupdate")
    @ResponseBody
    public String dataupdate(@RequestParam(value = "idsb") String idsb, @RequestParam(value = "timetype", defaultValue = "0") int timetype, HttpServletResponse response, HttpSession session) {
        JobWanted jobWanted=  jobWantedService.findByJobwantedId((long)session.getAttribute("jobWantedId"));
        jobWanted.setStep(2);
        jobWanted.setStepState(0);

        String[] idar = idsb.split(",");

        return timeTableService.lockcourse(idar, timetype, jobWanted);
    }
    //后台更新课程添加人数
    @RequestMapping("/updatetime")
    @ResponseBody
    public String updatetime(HttpServletResponse response, HttpSession session,
                             @RequestParam(value="tableCourseList") String tableCourseList
    ) {
        return timeTableService.updatetime(tableCourseList) ;
    }

}
