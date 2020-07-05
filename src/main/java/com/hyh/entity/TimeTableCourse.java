package com.hyh.entity;

import javax.persistence.*;

@Entity
@Table(name = "timetable_Course")
public class TimeTableCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int week;//星期几1~7
    private int courseType;//课程时间段
    private int Timetype;//1.周中上课(2345),2周中周末(4567),3周中周末(2367)

    private String date;//上课时间段
    private String text;//课程名称
    private int peopleNumber;//人数
    private int nuLock;//状态,0禁用,1可上,2试听,
    /*配置课程教师求职的多对一关系,
     * 使用注解的形式配置多对一关系
     * 1.配置表关系
     * 	@ManyToOne:配置多对一关系
     * 	targetEntity:对方实体类
     * 2.配置外键:
     * name:本表存储对放的id的列名,referencedColumnName:对方表id
     *
     * */
    @ManyToOne(targetEntity = JobWanted.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "timejobwanted",referencedColumnName = "jobwantedId")
    private JobWanted jobWanted;

    @ManyToOne(targetEntity = Teacher.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "teachertime",referencedColumnName = "teacherdId")
    private Teacher teacher;

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTimetype() {
        return Timetype;
    }

    public void setTimetype(int timetype) {
        Timetype = timetype;
    }

    public JobWanted getJobWanted() {
        return jobWanted;
    }

    public void setJobWanted(JobWanted jobWanted) {
        this.jobWanted = jobWanted;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }


    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getCourseType() {
        return courseType;
    }

    public void setCourseType(int courseType) {
        this.courseType = courseType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(int peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    public int getNuLock() {
        return nuLock;
    }

    public void setNuLock(int nuLock) {
        this.nuLock = nuLock;
    }

    public String getdata(){
        String color;

        switch (getNuLock()){
            case 0:
                color="lawngreen";
                break;
            case 1:
                color="sandybrown";
                break;
            case 2:
                color="GreenYellow";
                break;
            default:
                color="SlateGray";
                break;
        }


        return "'<div onclick=\"sub(this)\" tid=\""+this.id+"\" peopleNumber=\""+this.peopleNumber+"\"  style=\"background-color: "+color+"\";width: 100%;height: 100%;\">"+this.peopleNumber+"/2 </div>'";
     /*   return "'"+this.text+" '";*/
    }
}
