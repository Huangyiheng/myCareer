package com.hyh.service;

import com.alibaba.fastjson.JSONArray;
import com.maxwisdom.parallel.dao.JobWantedDao;
import com.maxwisdom.parallel.dao.TeacherDao;
import com.maxwisdom.parallel.dao.TimeTableDao;
import com.maxwisdom.parallel.dao.UsersDao;
import com.maxwisdom.parallel.entity.JobWanted;
import com.maxwisdom.parallel.entity.Teacher;
import com.maxwisdom.parallel.entity.TimeTableCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class TimeTableServiceimpl implements TimeTableService {
    @Autowired
    private TimeTableDao timeTableDao;
    @Autowired
    private UsersDao usersDao;
    @Autowired
    private JobWantedDao jobWantedDao;
    @Autowired
    private TeacherDao teacherDao;

    /*锁定课程*/
    public String lockcourse(String idar[], int timetype, JobWanted jobWanted) {

        //重置课程表格数据
        if (idar.length > 0) {

            Set<TimeTableCourse> timeTableCourseSet = jobWanted.getTimeTableCourseSet();
            for (TimeTableCourse timeTableCourse : timeTableCourseSet) {
                if (timetype != 0) {
                    timeTableCourse.setTimetype(timetype);
                }
                timeTableCourse.setNuLock(0);
            }
            jobWanted.setTimeTableCourseSet(timeTableCourseSet);

            jobWantedDao.save(jobWanted);
        }
//        将其与权限全部设为未锁定状态
        for (int i = 0; i < idar.length; i++) {
            TimeTableCourse timeTableCourse = timeTableDao.findById(Long.parseLong(idar[i])).get();
            timeTableCourse.setNuLock(1);
            timeTableCourse.setPeopleNumber(0);
            timeTableDao.save(timeTableCourse);
        }

        return "success";
    }

    /*后台修改时间*/
    public String updatetime(String json) {
        try {


            List<TimeTableCourse> timeTableCourseList = (List<TimeTableCourse>) JSONArray.parseArray(json, TimeTableCourse.class);

            //重置课程表格数据
            if (timeTableCourseList.size() > 0) {
                TimeTableCourse tableCourse = timeTableDao.findById(timeTableCourseList.get(0).getId()).get();
                List<TimeTableCourse> timeTableCourseSet;
                //查询全部
                if (tableCourse.getJobWanted() != null) {
                  timeTableCourseSet = timeTableDao.selectBytimejobwanted(tableCourse.getJobWanted().getJobwantedId());

                } else {
                    timeTableCourseSet = timeTableDao.selectBytimeteacher(tableCourse.getTeacher().getTeacherdId());
                }
                for (TimeTableCourse timeTableCourse : timeTableCourseSet) {
                    //将字段设为0
                    timeTableCourse.setNuLock(0);
                    timeTableCourse.setPeopleNumber(0);
                }
                timeTableDao.saveAll(timeTableCourseSet);
            }
//        将其与权限全部设为锁定状态
            for (TimeTableCourse timeTableCourse : timeTableCourseList) {
                TimeTableCourse timeTable = timeTableDao.findById(timeTableCourse.getId()).get();
                timeTable.setPeopleNumber(timeTableCourse.getPeopleNumber());
                timeTable.setNuLock(1);
                timeTableDao.save(timeTable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }


        /*初始化数据老师*/
    public boolean coursdatainitialiTeacher(Teacher teacher) {

        if (teacher == null) {
            return false;
        } else {
            Set<TimeTableCourse> timeTableCourseList = new HashSet<>();
            //  构建一周的数据
            for (int w = 1; w <= 7; w++) {

                for (int y = 1; y <= 17; y++) {
                    TimeTableCourse timeTableCourse = new TimeTableCourse();
                    // timeTableCourse.setJobWanted(jobWanted);
                    timeTableCourse.setWeek(w);
                    timeTableCourse.setTeacher(teacher);
                    timeTableCourse.setCourseType(y);
                    timeTableCourse.setText(w + "" + y);
                    switch (y) {
                        case 1:
                            timeTableCourse.setDate("09:00-9:30");
                            break;
                        case 2:
                            timeTableCourse.setDate("10:00-10:30");
                            break;
                        case 3:
                            timeTableCourse.setDate("10:40-11:10");
                            break;
                        case 4:
                            timeTableCourse.setDate("11:20-11:50");
                            break;
                        case 5:
                            timeTableCourse.setDate("13:30-14:00");
                            break;
                        case 6:
                            timeTableCourse.setDate("14:10-14:40");
                            break;
                        case 7:
                            timeTableCourse.setDate("14:50-15:20");
                            break;
                        case 8:
                            timeTableCourse.setDate("15:30-16:00");
                            break;
                        case 9:
                            timeTableCourse.setDate("16:10-16:30");
                            break;
                        case 10:
                            timeTableCourse.setDate("17:00-17:30");
                            break;
                        case 11:
                            timeTableCourse.setDate("17:40-18:10");
                            break;
                        case 12:
                            timeTableCourse.setDate("18:20-18:50");
                            break;
                        case 13:
                            timeTableCourse.setDate("19:00-19:30");
                            break;
                        case 14:
                            timeTableCourse.setDate("19:40-20:10");
                            break;
                        case 15:
                            timeTableCourse.setDate("20:20-20:50");
                            break;
                        case 16:
                            timeTableCourse.setDate("21:00-21:30");
                            break;
                        case 17:
                            timeTableCourse.setDate("21:40-22:10");
                            break;
                    }
                    timeTableCourse.setPeopleNumber(1);
                    timeTableCourseList.add(timeTableCourse);
                }
            }

            teacher.setTimeTableCourseSet(timeTableCourseList);
            teacherDao.save(teacher);

            //  jobWantedDao.save(jobWanted);
            // timeTableDao.saveAll(timeTableCourseList);
            return true;
        }


    }

    /*初始用户的课程表欧洲数据*/
    /*格鲁吉亚时间*/
    public boolean coursdatainitializeEurope(JobWanted jobWanted) {

        if (jobWanted == null) {
            return false;
        } else {
            Set<TimeTableCourse> timeTableCourseList = new HashSet<>();
            //  构建一周的数据
            for (int w = 1; w <= 7; w++) {

                for (int y = 1; y <= 17; y++) {
                    TimeTableCourse timeTableCourse = new TimeTableCourse();
                    // timeTableCourse.setJobWanted(jobWanted);
                    timeTableCourse.setWeek(w);
                    timeTableCourse.setJobWanted(jobWanted);
                    timeTableCourse.setCourseType(y);
                    timeTableCourse.setText(w + "" + y);
                    switch (y) {
                        case 1:
                            timeTableCourse.setDate("4:30-6:00");
                            break;
                        case 2:
                            timeTableCourse.setDate("6:00-6:30");
                            break;
                        case 3:
                            timeTableCourse.setDate("6:35-7:05");
                            break;
                        case 4:
                            timeTableCourse.setDate("7:10-7:40");
                            break;
                        case 5:
                            timeTableCourse.setDate("9:30-10:00");
                            break;
                        case 6:
                            timeTableCourse.setDate("10:05-10:35");
                            break;
                        case 7:
                            timeTableCourse.setDate("10:40-11:10");
                            break;
                        case 8:
                            timeTableCourse.setDate("11:15-11:45");
                            break;
                        case 9:
                            timeTableCourse.setDate("11:50-12:20");
                            break;
                        case 10:
                            timeTableCourse.setDate("13:00-13:30");
                            break;
                        case 11:
                            timeTableCourse.setDate("13:35-14:05");
                            break;
                        case 12:
                            timeTableCourse.setDate("14:10-14:40");
                            break;
                        case 13:
                            timeTableCourse.setDate("14:45-15:15");
                            break;
                        case 14:
                            timeTableCourse.setDate("15:20-15:50");
                            break;
                        case 15:
                            timeTableCourse.setDate("15:55-16:25");
                            break;
                        case 16:
                            timeTableCourse.setDate("16:30-17:00");
                            break;
                        case 17:
                            timeTableCourse.setDate("17:05-17:35");
                            break;
                    }
                    timeTableCourse.setPeopleNumber(1);
                    timeTableCourseList.add(timeTableCourse);
                }
            }

            jobWanted.setTimeTableCourseSet(timeTableCourseList);
            jobWantedDao.save(jobWanted);

            //  jobWantedDao.save(jobWanted);
            // timeTableDao.saveAll(timeTableCourseList);
            return true;
        }


    }


    /*初始化课程表北美时间*/
    public boolean coursdatainitializeNationality(JobWanted jobWanted) {

        if (jobWanted == null) {
            return false;
        } else {
            Set<TimeTableCourse> timeTableCourseList = new HashSet<>();
            //  构建一周的数据
            for (int w = 1; w <= 7; w++) {

                for (int y = 1; y <= 17; y++) {
                    TimeTableCourse timeTableCourse = new TimeTableCourse();
                    // timeTableCourse.setJobWanted(jobWanted);
                    timeTableCourse.setWeek(w);
                    timeTableCourse.setJobWanted(jobWanted);
                    timeTableCourse.setCourseType(y);
                    timeTableCourse.setText(w + "" + y);
                    switch (y) {
                        case 1:
                            timeTableCourse.setDate("1:35-2:05");
                            break;
                        case 2:
                            timeTableCourse.setDate("2:10-2:40");
                            break;
                        case 3:
                            timeTableCourse.setDate("2:45-3:15");
                            break;
                        case 4:
                            timeTableCourse.setDate("3:20-3:50");
                            break;
                        case 5:
                            timeTableCourse.setDate("3:55-4:25");
                            break;
                        case 6:
                            timeTableCourse.setDate("4:30-5:00");
                            break;
                        case 7:
                            timeTableCourse.setDate("5:05-5:35");
                            break;
                        case 8:
                            timeTableCourse.setDate("5:40-6:10");
                            break;
                        case 9:
                            timeTableCourse.setDate("7:20-7:50");
                            break;
                        case 10:
                            timeTableCourse.setDate("7:55-8:25");
                            break;
                        case 11:
                            timeTableCourse.setDate("8:30-9:00");
                            break;
                        case 12:
                            timeTableCourse.setDate("9:05-9:35");
                            break;
                        case 13:
                            timeTableCourse.setDate("20:30－21:00");
                            break;
                        case 14:
                            timeTableCourse.setDate("21:05-21:35");
                            break;
                        case 15:
                            timeTableCourse.setDate("21:40-22：10");
                            break;
                        case 16:
                            timeTableCourse.setDate("22:15-22:45");
                            break;
                        case 17:
                            timeTableCourse.setDate("22:50-23:20");
                            break;

                    }
                    timeTableCourse.setPeopleNumber(1);
                    timeTableCourseList.add(timeTableCourse);
                }
            }

            jobWanted.setTimeTableCourseSet(timeTableCourseList);
            jobWantedDao.save(jobWanted);

            //  jobWantedDao.save(jobWanted);
            // timeTableDao.saveAll(timeTableCourseList);
            return true;
        }


    }

    /*根据jobid返回数据*/
    public String databyjob(long jobid) {
        try {
            if (jobid == 0) {
                return "";
            } else {

                //从数据库获取用老师数据
                JobWanted jobWanted = jobWantedDao.findById(jobid).get();
                Set<TimeTableCourse> timeTableCourseSet = jobWanted.getTimeTableCourseSet();

                //如果课程为空则初始化课程数据
                if (timeTableCourseSet == null || timeTableCourseSet.size() == 0) {
                    //根据不同国家初始化不同时间
                    switch (jobWanted.getNationality()) {
                        case "0":
                            return "United State";

                        case "1":
                            if (!coursdatainitializeNationality(jobWanted)) {
                                return "初始化课程数据错误";
                            }
                            break;

                        case "2":
                            return "British";

                        case "3":
                            if (!coursdatainitializeEurope(jobWanted)) {
                                return "初始化课程数据错误";
                            }
                            break;

                        case "4":
                            return "Australia";

                        case "5":
                            return "New Zealand";

                        case "6":
                            return "Italy";

                        case "7":
                            return "Ukraine";

                        case "8":
                            return "Russia";

                        case "9":
                            return "Sorth Africa";

                        case "10":
                            return "Denmark";

                        case "11":
                            return "Switzerland";
                        case "12":
                            return "Germany";
                        default:
                            return "数据异常";

                    }


                }


                StringBuffer week = new StringBuffer("[");
                for (int w = 1; w <= 7; w++) {
                    /*['<div ', '大学英语(Ⅳ)@10203', '', '', '', '', '毛概@14208', '毛概@14208', '', '', '', '选修'],*/
                    StringBuffer coursetype = new StringBuffer("[");

                    for (int y = 1; y <= 17; y++) {
                        TimeTableCourse timeTableCourse = new TimeTableCourse();
                        timeTableCourse = timeTableDao.selectbytypebyweekbyjobid(y, w, jobid);

                        String colorclass = "";
                        if (timeTableCourse.getNuLock() == 1) {
                            colorclass = "selecttime";
                        }

                        String data = "'<div class=\"tbdiv  " + colorclass + "  " + w + y + "\" onclick=\"sub(this)\" tid=\"" + timeTableCourse.getId() + "\" peopleNumber=\"" + timeTableCourse.getPeopleNumber() + "\"   time=\"" + timeTableCourse.getDate() + " \"  >" + timeTableCourse.getDate() + "</div>'";


                        coursetype.append(data);
                        if (y < 17) {
                            coursetype.append(",");
                        }
                    }
                    coursetype.append("]");
                    if (w < 7) {
                        coursetype.append(",");
                    }
                    week.append(coursetype);
                }
                week.append("]");
                System.out.println(week.toString());
                return week.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String databyteacher(long teacheid) {
        try {
            if (teacheid == 0) {
                return "";
            } else {

                //从数据库获取用老师数据
                Teacher teacher = teacherDao.findById(teacheid).get();
                Set<TimeTableCourse> timeTableCourseSet = teacher.getTimeTableCourseSet();

                //如果课程为空则初始化课程数据
                if (timeTableCourseSet == null || timeTableCourseSet.size() == 0) {
                    //根据不同国家初始化不同时间
                    if (!coursdatainitialiTeacher(teacher)) {
                        return "初始化课程数据错误";
                    }
                }


                StringBuffer week = new StringBuffer("[");
                for (int w = 1; w <= 7; w++) {
                    /*['<div ', '大学英语(Ⅳ)@10203', '', '', '', '', '毛概@14208', '毛概@14208', '', '', '', '选修'],*/
                    StringBuffer coursetype = new StringBuffer("[");

                    for (int y = 1; y <= 17; y++) {
                        TimeTableCourse timeTableCourse = new TimeTableCourse();
                        timeTableCourse = timeTableDao.selectbytypebyweekbyteacherid(y, w, teacheid);

                        String colorclass = "";
                        if (timeTableCourse.getNuLock() == 1) {
                            colorclass = "selecttime";
                        }

                        String data = "'<div class=\"tbdiv  " + colorclass + "  " + w + y + "\" onclick=\"sub(this)\" tid=\"" + timeTableCourse.getId() + "\" peopleNumber=\"" + timeTableCourse.getPeopleNumber() + "\"   time=\"" + timeTableCourse.getDate() + " \"  >" + timeTableCourse.getDate() + "</div>'";


                        coursetype.append(data);
                        if (y < 17) {
                            coursetype.append(",");
                        }
                    }
                    coursetype.append("]");
                    if (w < 7) {
                        coursetype.append(",");
                    }
                    week.append(coursetype);
                }
                week.append("]");
                System.out.println(week.toString());
                return week.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    /*返回数据格鲁吉亚*/
    public String dataEurope(JobWanted jobWanted) {
        try {
            if (jobWanted == null) {
                return "";
            } else {

                //从数据库获取用户数据
                // TimeTableCourse timeTableCourse=new ArrayList<TimeTableCourse>(user.getJobWanted().getTimeTableCourseSet()).get(0);

                Long jobwantedId = jobWanted.getJobwantedId();
                Set<TimeTableCourse> timeTableCourseSet = jobWanted.getTimeTableCourseSet();

                //如果课程为空则初始化课程数据
                if (timeTableCourseSet == null || timeTableCourseSet.size() == 0) {
                    if (!coursdatainitializeEurope(jobWanted)) {
                        return "初始化课程数据错误";
                    }
                }


                StringBuffer week = new StringBuffer("[");
                for (int w = 1; w <= 7; w++) {
                    /*['<div ', '大学英语(Ⅳ)@10203', '', '', '', '', '毛概@14208', '毛概@14208', '', '', '', '选修'],*/
                    StringBuffer coursetype = new StringBuffer("[");

                    for (int y = 1; y <= 17; y++) {
                        TimeTableCourse timeTableCourse = new TimeTableCourse();
                        timeTableCourse = timeTableDao.selectbytypebyweekbyjobid(y, w, jobwantedId);

                        String colorclass = "";
                        if (timeTableCourse.getNuLock() == 1) {
                            colorclass = "selecttime";
                        }

                        String data = "'<div class=\"tbdiv  " + colorclass + "  " + w + y + "\" onclick=\"sub(this)\" tid=\"" + timeTableCourse.getId() + "\" peopleNumber=\"" + timeTableCourse.getPeopleNumber() + "\"  >" + timeTableCourse.getDate() + "</div>'";


                        coursetype.append(data);
                        if (y < 17) {
                            coursetype.append(",");
                        }
                    }
                    coursetype.append("]");
                    if (w < 7) {
                        coursetype.append(",");
                    }
                    week.append(coursetype);
                }
                week.append("]");
                System.out.println(week.toString());
                return week.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    /*返回数据加拿大*/
    public String dataNationality(JobWanted jobWanted) {
        try {
            if (jobWanted == null) {
                return "";
            } else {

                //从数据库获取用户数据
                // TimeTableCourse timeTableCourse=new ArrayList<TimeTableCourse>(user.getJobWanted().getTimeTableCourseSet()).get(0);

                Long jobwantedId = jobWanted.getJobwantedId();
                Set<TimeTableCourse> timeTableCourseSet = jobWanted.getTimeTableCourseSet();

                //如果课程为空则初始化课程数据
                if (timeTableCourseSet == null || timeTableCourseSet.size() == 0) {
                    if (!coursdatainitializeNationality(jobWanted)) {
                        return "初始化课程数据错误";
                    }
                }

                StringBuffer week = new StringBuffer("[");
                for (int w = 1; w <= 7; w++) {
                    /*['<div ', '大学英语(Ⅳ)@10203', '', '', '', '', '毛概@14208', '毛概@14208', '', '', '', '选修'],*/
                    StringBuffer coursetype = new StringBuffer("[");

                    for (int y = 1; y <= 17; y++) {
                        TimeTableCourse timeTableCourse = new TimeTableCourse();
                        timeTableCourse = timeTableDao.selectbytypebyweekbyjobid(y, w, jobwantedId);

                        String colorclass = "";
                        if (timeTableCourse.getNuLock() == 1) {
                            colorclass = "selecttime";
                        }

                        String data = "'<div class=\"tbdiv  " + colorclass + "  " + w + y + "\" onclick=\"sub(this)\" tid=\"" + timeTableCourse.getId() + "\" peopleNumber=\"" + timeTableCourse.getPeopleNumber() + "\"  >" + timeTableCourse.getDate() + "</div>'";


                        coursetype.append(data);
                        if (y < 17) {
                            coursetype.append(",");
                        }
                    }
                    coursetype.append("]");
                    if (w < 7) {
                        coursetype.append(",");
                    }
                    week.append(coursetype);
                }
                week.append("]");
                System.out.println(week.toString());
                return week.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }


}
