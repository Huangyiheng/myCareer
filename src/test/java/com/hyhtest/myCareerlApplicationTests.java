package com.hyhtest;

import com.hyh.service.MailUtilService;
import com.hyh.utility.MailBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class myCareerlApplicationTests {

    @Autowired
    MailUtilService mailUtilService;

    @Test
    public void senemali() {

        MailBean mailBean = new MailBean();
        mailBean.setToAccount("hyh69355396@163.com");//收件地址
        mailBean.setSubject("Paral teacher");//邮件主题
        mailBean.setContent("Dear huangyih    " +
                "Thank you for your interest in PARAL TEACHER！" +
                "Congratulations on making it to the next step in our " +
                "application process.\n" +
                "\n" +
                "First, Please download the WeChat mobile APP to your " +
                "smartphone,we will use this APP to contact you.\n" +
                "\n" +
                "Then, search the number：152 101 85017 to add contact." +
                "We will help you to arrange an interview .\n" +
                "\n" +
                "Thanks and best regard." +
                "\n" +
                "\n" +
                "\n" +
                "                 PARAL TEACHER, Beijing, China");//发送内容
        String path = System.getProperty("user.dir");
        File file = new File(path + "\\src\\main\\resources\\static\\images\\system\\youjianhuifutu.png");
        mailBean.setAttachmentFile(file);//发送附件
        mailBean.setAttachmentFileName("PARAL_TEACHER_IMG");//附件名称
        mailUtilService.sendMailAttachment(mailBean);
    }


    @Test
    public void test() {

    }
}
