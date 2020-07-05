package com.hyh.service;

import com.maxwisdom.parallel.utility.MailBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;

@Service
@Transactional
public class MailUtilService implements MailUtil {
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    MailProperties mailProperties;

    /**
     * 发送邮件测试方法
     */
    public void sendMail(MailBean mailBean) {
        SimpleMailMessage mimeMessage = new SimpleMailMessage();
        mimeMessage.setFrom(mailProperties.getUsername());
        mimeMessage.setTo(mailBean.getToAccount());
        mimeMessage.setSubject(mailBean.getSubject());
        mimeMessage.setText(mailBean.getContent());
        mailSender.send(mimeMessage);
    }


    /**
     * 发送邮件-附件邮件
     *
     * @param boMailBean
     */
    public boolean sendMailAttachment(MailBean mailBean) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(mailProperties.getUsername());
            helper.setTo(mailBean.getToAccount());
            helper.setSubject(mailBean.getSubject());
            helper.setText(mailBean.getContent(), true);
            // 增加附件名称和附件
            helper.addAttachment(MimeUtility.encodeWord(mailBean.getAttachmentFileName(), "utf-8", "B"), mailBean.getAttachmentFile());
           // helper.addInline("1",mailBean.getAttachmentFile());
            mailSender.send(mimeMessage);
            return true;
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
    }






}




