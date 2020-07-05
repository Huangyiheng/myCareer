package com.hyh.service;

import com.maxwisdom.parallel.entity.JobWanted;

import javax.servlet.http.HttpServletResponse;

public interface JobWantedService {




    /*教师筛选分页查询*/
    public String jobwantedselect(JobWanted jobw, HttpServletResponse response) ;

    public String recruitment_lecturer_delete(Integer[] IDArray) ;
    public String updateShowTeacher(JobWanted jobWanted) ;
    public String findByshowTeacher(JobWanted jobw);
    public JobWanted findByJobwantedId(long id);
    public JobWanted findByEMail(String email);
    public String update(JobWanted jobWanted) ;
    public void  sendmailsuccess(JobWanted jobwanted);


}
