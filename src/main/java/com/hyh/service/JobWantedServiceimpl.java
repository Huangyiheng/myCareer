package com.hyh.service;

import com.alibaba.fastjson.JSON;
import com.maxwisdom.parallel.dao.FilePropertiesDao;
import com.maxwisdom.parallel.dao.JobWantedDao;
import com.maxwisdom.parallel.entity.JobWanted;
import com.maxwisdom.parallel.utility.DataModel;
import com.maxwisdom.parallel.utility.MailBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class JobWantedServiceimpl implements JobWantedService {
    @Autowired
    private MailUtilService mailUtilService;
    @Autowired
    private FilePropertiesDao filedao;
    @Autowired
    private FileService fileService;
    @Autowired
    private JobWantedDao jobwanteddao;

    public String findByshowTeacher(JobWanted jobw) {
        Specification<JobWanted> spec = new Specification<JobWanted>() {

            public Predicate toPredicate(Root<JobWanted> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                List<Predicate> list = new ArrayList<>();
                list.add(criteriaBuilder.equal(root.get("showTeacher").as(String.class), 1));//首页展示外教,0false,1true

                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        jobw.setOrderDir("desc");
        jobw.setOrderStr("createdtime");
        Sort sort;
        switch (jobw.getOrderDir()) {
            case "asc": {
                sort = new Sort(Sort.Direction.ASC, jobw.getOrderStr());
                break;
            }
            case "desc": {
                sort = new Sort(Sort.Direction.DESC, jobw.getOrderStr());
                break;
            }
            default: {
                sort = new Sort(Sort.Direction.DESC, jobw.getOrderStr());
            }
        }

        Pageable pageable = new PageRequest(jobw.getPageNum() - 1, jobw.getLength(), sort);
        Page<JobWanted> page = jobwanteddao.findAll(spec, pageable);
        return JSON.toJSONString(page);
    }


    public String updateShowTeacher(JobWanted jobWantednew) {
        try {
            JobWanted jobWanted = jobwanteddao.findById(jobWantednew.getJobwantedId()).get();
            jobWanted.setShowTeacher(jobWantednew.getShowTeacher());
            jobwanteddao.save(jobWanted);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "success";
    }

    //更新
    public String update(JobWanted jobWantednew) {
        try {
            JobWanted jobWanted = jobwanteddao.findById(jobWantednew.getJobwantedId()).get();
            jobWanted.setStepState(jobWantednew.getStepState());

            new Thread() {
                public void run() {


                        sendmailtype(jobWanted);//发送状态更新邮件

                };
            }.start();

            jobwanteddao.save(jobWanted);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "success";
    }
    //发送状态更新邮件
    public void  sendmailtype(JobWanted jobwanted){
        System.out.println("教师招聘成功回复");
        MailBean mailBean=new MailBean();
        mailBean.setToAccount(jobwanted.geteMail());//收件地址
        mailBean.setSubject("Paral teacher");//邮件主题

        mailBean.setContent("Dear "+jobwanted.getFirstName()+""+
                "\n"+
                "Your teacher application status has been updated. You can now go to the website www.pingxingwaijao.com/job for the next step"+
                "\n"+
                "\n"+
                "\n"+
                "                 PARAL TEACHER, Beijing, China");//发送内容
        String path=System.getProperty("user.dir");
        File file = new File(path+"\\src\\main\\resources\\static\\images\\system\\youjianhuifutu.png");
        mailBean.setAttachmentFile(file);//发送附件
        mailBean.setAttachmentFileName("PARAL_TEACHER_IMG");//附件名称
        mailUtilService.sendMail(mailBean);//发送纯文本邮件

    }
    //发送招聘成功邮件
    public void  sendmailsuccess(JobWanted jobwanted){
        System.out.println("教师招聘动态，邮件回复");
        MailBean mailBean=new MailBean();
        mailBean.setToAccount(jobwanted.geteMail());//收件地址
        mailBean.setSubject("Paral teacher");//邮件主题

        mailBean.setContent("Dear "+jobwanted.getFirstName()+"    " +
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

    }
    /*教师筛选分页查询*/
    public String jobwantedselect(JobWanted jobw, HttpServletResponse response) {

        /*匿名内部类
         * Pageable,分页参数
         * 创建PageRequerst的过程中,需要调用他的构造方法传入两个参数
         * 1.当前查询的页数(从0开始)
         * 2.每页查询条数
         */
        System.out.println("进入招聘老师数据查询");
        Specification<JobWanted> spec = new Specification<JobWanted>() {

            public Predicate toPredicate(Root<JobWanted> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                List<Predicate> list = new ArrayList<>();
                if (!StringUtils.isEmpty(jobw.getFirstName())) {
                    list.add(criteriaBuilder.like(root.get("firstName").as(String.class), "%" + jobw.getFirstName() + "%"));
                }
                if (!StringUtils.isEmpty(jobw.geteMail())) {
                    list.add(criteriaBuilder.like(root.get("eMail").as(String.class), "%" + jobw.geteMail() + "%"));
                }

                if (!StringUtils.isEmpty(jobw.getNationality())) {
                    list.add(criteriaBuilder.like(root.get("nationality").as(String.class), jobw.getNationality()));
                }
                if (!StringUtils.isEmpty(jobw.getDiploma())) {
                    list.add(criteriaBuilder.like(root.get("diploma").as(String.class), jobw.getDiploma()));
                }
                if (!StringUtils.isEmpty(jobw.getTeachingEnglish())) {
                    list.add(criteriaBuilder.like(root.get("teachingEnglish").as(String.class), jobw.getTeachingEnglish()));
                }
                if (!StringUtils.isEmpty(jobw.getParticipateYour())) {
                    list.add(criteriaBuilder.like(root.get("participateYour").as(String.class), jobw.getParticipateYour()));
                }
                if (!StringUtils.isEmpty(jobw.getDiplomaReceived())) {
                    list.add(criteriaBuilder.like(root.get("diplomaReceived").as(String.class), jobw.getDiplomaReceived()));
                }
                if (!StringUtils.isEmpty(jobw.getTeachingCertification())) {
                    list.add(criteriaBuilder.like(root.get("teachingCertification").as(String.class), jobw.getTeachingCertification()));
                }

                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };


        Sort sort;
        switch (jobw.getOrderDir()) {
            case "asc": {
                sort = new Sort(Sort.Direction.ASC, jobw.getOrderStr());
                break;
            }
            case "desc": {
                sort = new Sort(Sort.Direction.DESC, jobw.getOrderStr());
                break;
            }
            default: {
                sort = new Sort(Sort.Direction.DESC, jobw.getOrderStr());
            }
        }


        //安装报名日期倒序
        //Sort sort = new Sort(Sort.Direction.DESC, "age");
        Pageable pageable = new PageRequest(jobw.getPageNum() - 1, jobw.getLength(), sort);
        List<JobWanted> jobWanteds = jobwanteddao.findAll();
        Page<JobWanted> page = jobwanteddao.findAll(spec, pageable);


        //返回datatables数据模型
        return JSON.toJSONString(DataModel.getSucDataModal(page.getContent(), (int) page.getTotalElements(), jobw.getDraw()));

    }

    public JobWanted findByJobwantedId(long id) {
        return jobwanteddao.findByJobwantedId(id);
    }

    public JobWanted findByEMail(String email) {
        return jobwanteddao.findByEMail(email);
    }


    public String recruitment_lecturer_delete(Integer[] IDArray) {

        System.out.println("招聘老师删除数据");
        JobWanted job;
        Map<String, Map> filemaperror = new HashMap<>();
        if (IDArray != null) {
            for (Integer id : IDArray) {
                job = jobwanteddao.findById(id.longValue()).get();//查询该对象
                boolean filetrue = true;
                //删除失败集合

                if (job != null) {
                    //记录错误信息
                    Map<String, String> filemap = new HashMap<>();
                    //删除个人照片
                    if (job.getP_yourself() != null ) {
                        if(fileService.filedelete(job.getP_yourself().getPath())){
                            //文件删除成功后执行
                            filemap.put("个人照片", "true");
                        }else {
                            filemap.put("个人照片", "false");
                            filetrue = false;
                        }
                    }

                    //删除个人视频
                    if (job.getPersonal_video()!=null) {
                        if(fileService.filedelete(job.getPersonal_video().getPath())){
                            //文件删除成功后执行
                            filemap.put("个人视频", "true");
                        }else {
                            filemap.put("个人视频", "false");
                            filetrue = false;
                        }
                    }
                    //删除护照照片
                  /*  if (job.getP_passport()!=null&& filedelete(job.getP_passport().getPath())) {
                        //文件删除成功后执行
                        filemap.put("护照照片", "true");
                    } else {
                        filemap.put("护照照片", "false");
                        filetrue = false;
                    }
                    //删除简历
                    if (job.getCv()!=null&& filedelete(job.getCv().getPath())) {
                        //文件删除成功后执行
                        filemap.put("简历", "true");
                    } else {
                        filemap.put("简历", "false");
                        filetrue = false;
                    }
                    //删除个人视频
                    if (job.getPersonal_video()!=null&& filedelete(job.getPersonal_video().getPath())) {
                        //文件删除成功后执行
                        filemap.put("个人视频", "true");
                    } else {
                        filemap.put("个人视频", "false");
                        filetrue = false;
                    }
                    //删除学历证书
                    if (job.getQualifications()!=null&& filedelete(job.getQualifications().getPath())) {
                        //文件删除成功后执行
                        filemap.put("学历证书", "true");
                    } else {
                        filemap.put("学历证书", "false");
                        filetrue = false;
                    }*/
                    //如果全部删除成功则删除数据库记录
                    if (filetrue) {
                        jobwanteddao.delete(job);
                    } else {
                        filemaperror.put(job.getFirstName(), filemap);
                    }
                }
            }
            if (filemaperror.size() < 1) {
                return "success";
            } else {
                return JSON.toJSONString(filemaperror);
            }
        }
        return "null";
    }


}
