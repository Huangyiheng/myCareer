package com.hyh.entity;


import com.maxwisdom.parallel.utility.PageForm;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Table(name = "teacher")
@Entity
public class Teacher extends PageForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teacherdId;
    private String teacherName;
    private String text;//描述文字
    private String school;//毕业学校
    private String schoolTimeStar;//入学时间
    private String schoolTimeEnd;//毕业时间
    private int showTeacher;//首页展示外教,0false,1true
    private String nationality;//地区
    private Date createdtime;//创建日期

    @OneToOne(targetEntity = FileProperties.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "homePicture", referencedColumnName = "fileId")//name本实体类对应的数据库列名,referencedColumnName对应文件类关联的id
    private FileProperties picture;//首页显示照片

    @OneToOne(targetEntity = FileProperties.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "video", referencedColumnName = "fileId")//name本实体类对应的数据库列名,referencedColumnName对应文件类关联的id
    private FileProperties video;//首页显示视频
    //多对一,name为timetables的列名,teacherid为引用的本id
    @OneToMany(targetEntity = TimeTableCourse.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "timeTeacher", referencedColumnName = "teacherdId")
    private Set<TimeTableCourse> timeTableCourseSet;

    public Date getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(Date createdtime) {
        this.createdtime = createdtime;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Long getTeacherdId() {
        return teacherdId;
    }

    public void setTeacherdId(Long teacherdId) {
        this.teacherdId = teacherdId;
    }

    public int getShowTeacher() {
        return showTeacher;
    }

    public void setShowTeacher(int showTeacher) {
        this.showTeacher = showTeacher;
    }
    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSchoolTimeStar() {
        return schoolTimeStar;
    }

    public void setSchoolTimeStar(String schoolTimeStar) {
        this.schoolTimeStar = schoolTimeStar;
    }

    public String getSchoolTimeEnd() {
        return schoolTimeEnd;
    }

    public void setSchoolTimeEnd(String schoolTimeEnd) {
        this.schoolTimeEnd = schoolTimeEnd;
    }

    public FileProperties getPicture() {
        return picture;
    }

    public void setPicture(FileProperties picture) {
        this.picture = picture;
    }

    public FileProperties getVideo() {
        return video;
    }

    public void setVideo(FileProperties video) {
        this.video = video;
    }

    public Set<TimeTableCourse> getTimeTableCourseSet() {
        return timeTableCourseSet;
    }

    public void setTimeTableCourseSet(Set<TimeTableCourse> timeTableCourseSet) {
        this.timeTableCourseSet = timeTableCourseSet;
    }
}
