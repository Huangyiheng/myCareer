package com.hyh.utility;

import java.io.File;

public class MailBean {



        private String subject;//邮件主题
        private String content;//发送内容
        private String toAccount;//收件地址
        private File attachmentFile;//发送附件
        private String attachmentFileName;//附件名称

        public String getSubject() {
                return subject;
        }

        public void setSubject(String subject) {
                this.subject = subject;
        }

        public String getContent() {
                return content;
        }

        public void setContent(String content) {
                this.content = content;
        }

        public String getToAccount() {
                return toAccount;
        }

        public void setToAccount(String toAccount) {
                this.toAccount = toAccount;
        }

        public File getAttachmentFile() {
                return attachmentFile;
        }

        public void setAttachmentFile(File attachmentFile) {
                this.attachmentFile = attachmentFile;
        }

        public String getAttachmentFileName() {
                return attachmentFileName;
        }

        public void setAttachmentFileName(String attachmentFileName) {
                this.attachmentFileName = attachmentFileName;
        }
}
