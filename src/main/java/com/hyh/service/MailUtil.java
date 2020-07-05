package com.hyh.service;


import com.hyh.utility.MailBean;

public interface MailUtil {
    public void sendMail(MailBean mailBean);


    /**
     * 发送邮件-附件邮件
     *
     * @param
     */
    public boolean sendMailAttachment(MailBean mailBean);
}