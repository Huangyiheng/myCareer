/**
 * 作者:HYH
 * 功能:前台页面控制器
 * 时间:2020年3月18日
 */
package com.hyh.controller;

import com.maxwisdom.parallel.dao.UsersDao;
import com.maxwisdom.parallel.entity.User;
import com.maxwisdom.parallel.service.MailUtilService;
import com.maxwisdom.parallel.utility.MailBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author HYH
 * ConfigurationProperties:使用注解获取application.yml中的
 * 属性,person为配置文件中的对象名称,自动对下面的name变量进行赋值,可以不写@Value("${name}")
 * 发送邮件
 */
@RequestMapping("/sendmail")
@Controller
//@RequestMapping("/back")
//@ConfigurationProperties(prefix = "person")
public class SendMailController {
    @Autowired
    private MailUtilService mailUtilService;
    @Autowired
    private UsersDao usersDao;


    /*教师招聘成功回复*/
    @RequestMapping("/jobreply")
    public String maxwisdombackhomedf(HttpSession session) throws FileNotFoundException {
        User user = (User) session.getAttribute("user");
        user = usersDao.findById(user.getId()).get();
        System.out.println("教师招聘成功回复");
        MailBean mailBean=new MailBean();
        mailBean.setToAccount(user.getJobWanted().geteMail());//收件地址
        mailBean.setSubject("Paral teacher");//邮件主题
        /*Dear  ------,
                Thank you for your interest in PARAL TEACHER！Congratulations on making it to the next step in our application process.
        First, please download the WhatsApp mobile APP to your smartphone,we will use this APP to contact you.
        Then, search the number：NUMBER to add contact .We will help you to arrange an interview .
        Thanks and best regard.
                                                                      
                        PARAL TEACHER, Beijing, China*/

       /* Dear  ------,
                Thank you for your interest in PARAL TEACHER！Congratulations on making it to the next step in our application process.
        First, please download the WhatsApp mobile APP to your smartphone,we will use this APP to contact you.
        Then, search the number：598988617 to add contact .We will help you to arrange an interview .
        Thanks and best regard.
                                                                      
                        PARAL TEACHER, Beijing, China*/
        mailBean.setContent("Dear "+user.getJobWanted().getFirstName()+"\n" +
                "Thank you for your interest in PARAL TEACHER！" +
                "Congratulations on making it to the next step in our " +
                "application process.\n" +
                "\n"+
                "First, please download the WhatsApp mobile APP to your " +
                "smartphone,we will use this APP to contact you.\n" +
                "\n"+
                "Then, search the number:598988617  to add contact." +
                "We will help you to arrange an interview .\n" +
                "\n"+
                "Thanks and best regard." +
                "\n"+
                "\n"+
                "\n"+
                "                 PARAL TEACHER, Beijing, China");//发送内容
        String path=System.getProperty("user.dir");
        File file = new File(path+"\\src\\main\\resources\\static\\images\\system\\youjianhuifutu.png");
        mailBean.setAttachmentFile(file);//发送附件
        mailBean.setAttachmentFileName("PARAL_TEACHER_IMG");//附件名称
        mailUtilService.sendMail(mailBean);//发送纯文本邮件
        return "sucess";
    }


}
