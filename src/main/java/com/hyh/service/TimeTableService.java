package com.hyh.service;


import com.maxwisdom.parallel.entity.JobWanted;

public interface TimeTableService {
    public String dataEurope(JobWanted jobWanted);
    public String dataNationality(JobWanted jobWanted);
    public String databyjob(long jobid);
    public String databyteacher(long jobid);

    public String lockcourse(String idar[], int timetype, JobWanted jobWanted);

    public boolean coursdatainitializeEurope(JobWanted jobWanted);
    public boolean coursdatainitializeNationality(JobWanted jobWanted) ;
    public String updatetime(String json) ;
}
