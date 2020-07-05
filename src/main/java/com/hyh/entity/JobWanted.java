package com.hyh.entity;


import com.maxwisdom.parallel.utility.PageForm;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Table(name = "job_wanted")
@Entity
public class JobWanted extends PageForm implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobwantedId;
    private String firstName;//名字
    private String surName;//姓
    private String phoneNumber;//手机号码
    private String eMail;//电子邮箱
    private String password;//密码，邮箱作为登录名
    private Date dateOfBirth;//生日
    private String gender;//性别
    private String nationality;//国籍
    private String teachingEnglish;//英语任教时长
    private String onlineTeaching;//当在线老师多久
    private String studentAges;//学生年龄
    private String currentlyEmployed;//现在有工作吗?
    private String typeWork;//什么样的工作?
    private String teachingJob;//你想找什么样的工作
    private String participateYour;//你想在工作中投入多少时间
    private String diploma;//教学文凭
    private String educationName;//教育学校名称
    private Date educationStartTime;//教育---结束时间
    private Date educationEndTime;//教育---结束时间
    private String diplomaReceived;//获得凭证
    private String teachingCertification;//英语教学认证
    private int step;//0.表单填写，1表单审核,2选择课程审核,3面试,4签订合同,完成
    private int stepState;//0未审核,1通过,2未通过

    private int showTeacher;//首页展示外教,0false,1true
    private String brief;//简介


    @OneToOne(cascade = CascadeType.ALL)//People是关系的维护端，当删除 people，会级联删除 address
    @JoinColumn(name = "userId", referencedColumnName = "id")//name本实体类对应的数据库列名,referencedColumnName对应文件类关联的id
    private User user;


    @OneToMany(targetEntity = TimeTableCourse.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "timejobwanted", referencedColumnName = "jobwantedId")
    private Set<TimeTableCourse> timeTableCourseSet;


    @OneToOne(targetEntity = FileProperties.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "pYourself", referencedColumnName = "fileId")//name本实体类对应的数据库列名,referencedColumnName对应文件类关联的id
    private FileProperties p_yourself;//个人照片


    @OneToOne(targetEntity = FileProperties.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "pPassport", referencedColumnName = "fileId")//name本实体类对应的数据库列名,referencedColumnName对应文件类关联的id
    private FileProperties p_passport;//护照照片

    @OneToOne(targetEntity = FileProperties.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "cv", referencedColumnName = "fileId")//name本实体类对应的数据库列名,referencedColumnName对应文件类关联的id
    private FileProperties cv;//简历

    @OneToOne(targetEntity = FileProperties.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "personalVideo", referencedColumnName = "fileId")
//name本实体类对应的数据库列名,referencedColumnName对应文件类关联的id
    private FileProperties personal_video;//个人视频

    @OneToOne(targetEntity = FileProperties.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "qualifications", referencedColumnName = "fileId")
//name本实体类对应的数据库列名,referencedColumnName对应文件类关联的id
    private FileProperties qualifications;//学历证书

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getStepState() {
        return stepState;
    }

    public void setStepState(int stepState) {
        this.stepState = stepState;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public int getShowTeacher() {
        return showTeacher;
    }

    public void setShowTeacher(int showTeacher) {
        this.showTeacher = showTeacher;
    }

    private Date createdtime;//创建日期

    public Set<TimeTableCourse> getTimeTableCourseSet() {
        return timeTableCourseSet;
    }

    public Long getJobwantedId() {
        return jobwantedId;
    }

    public void setJobwantedId(Long jobwantedId) {
        this.jobwantedId = jobwantedId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getTeachingEnglish() {
        return teachingEnglish;
    }

    public void setTeachingEnglish(String teachingEnglish) {
        this.teachingEnglish = teachingEnglish;
    }

    public String getOnlineTeaching() {
        return onlineTeaching;
    }

    public void setOnlineTeaching(String onlineTeaching) {
        this.onlineTeaching = onlineTeaching;
    }

    public String getStudentAges() {
        return studentAges;
    }

    public void setStudentAges(String studentAges) {
        this.studentAges = studentAges;
    }

    public String getCurrentlyEmployed() {
        return currentlyEmployed;
    }

    public void setCurrentlyEmployed(String currentlyEmployed) {
        this.currentlyEmployed = currentlyEmployed;
    }

    public String getTypeWork() {
        return typeWork;
    }

    public void setTypeWork(String typeWork) {
        this.typeWork = typeWork;
    }

    public String getTeachingJob() {
        return teachingJob;
    }

    public void setTeachingJob(String teachingJob) {
        this.teachingJob = teachingJob;
    }

    public String getParticipateYour() {
        return participateYour;
    }

    public void setParticipateYour(String participateYour) {
        this.participateYour = participateYour;
    }

    public String getDiploma() {
        return diploma;
    }

    public void setDiploma(String diploma) {
        this.diploma = diploma;
    }

    public String getEducationName() {
        return educationName;
    }

    public void setEducationName(String educationName) {
        this.educationName = educationName;
    }

    public Date getEducationStartTime() {
        return educationStartTime;
    }

    public void setEducationStartTime(Date educationStartTime) {
        this.educationStartTime = educationStartTime;
    }

    public Date getEducationEndTime() {
        return educationEndTime;
    }

    public void setEducationEndTime(Date educationEndTime) {
        this.educationEndTime = educationEndTime;
    }

    public String getDiplomaReceived() {
        return diplomaReceived;
    }

    public void setDiplomaReceived(String diplomaReceived) {
        this.diplomaReceived = diplomaReceived;
    }

    public String getTeachingCertification() {
        return teachingCertification;
    }

    public void setTeachingCertification(String teachingCertification) {
        this.teachingCertification = teachingCertification;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTimeTableCourseSet(Set<TimeTableCourse> timeTableCourseSet) {
        this.timeTableCourseSet = timeTableCourseSet;
    }

    public FileProperties getP_yourself() {
        return p_yourself;
    }

    public void setP_yourself(FileProperties p_yourself) {
        this.p_yourself = p_yourself;
    }

    public FileProperties getP_passport() {
        return p_passport;
    }

    public void setP_passport(FileProperties p_passport) {
        this.p_passport = p_passport;
    }

    public FileProperties getCv() {
        return cv;
    }

    public void setCv(FileProperties cv) {
        this.cv = cv;
    }

    public FileProperties getPersonal_video() {
        return personal_video;
    }

    public void setPersonal_video(FileProperties personal_video) {
        this.personal_video = personal_video;
    }

    public FileProperties getQualifications() {
        return qualifications;
    }

    public void setQualifications(FileProperties qualifications) {
        this.qualifications = qualifications;
    }

    public Date getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(Date createdtime) {
        this.createdtime = createdtime;
    }
}
