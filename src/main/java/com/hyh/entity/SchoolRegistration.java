package com.hyh.entity;


import com.maxwisdom.parallel.utility.PageForm;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Table(name = "school_registration")
@Entity
public class SchoolRegistration extends PageForm implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "schoolbabyname")
    private String schoolbabyname;
    @Column(name = "schooladdress")
    private String schooladdress;
    @Column(name = "schoolphoneNumber")
    private String schoolphoneNumber;
    @Column(name = "schoolphone")
    private String schoolphone;
    @Column(name = "schoolnum")
    private String schoolnum;
    @Column(name = "schoolage")
    private String schoolage;
    @Column(name = "campus")
    private String campus;
    @Column(name = "course")
    private String course;
    @Column(name = "createtime")
    private Date createtime;
    @Column(name = "updatetime")
    private Date updatetime;
    @Column(name = "relation")
    private String relation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSchoolbabyname() {
        return schoolbabyname;
    }

    public void setSchoolbabyname(String schoolbabyname) {
        this.schoolbabyname = schoolbabyname;
    }

    public String getSchooladdress() {
        return schooladdress;
    }

    public void setSchooladdress(String schooladdress) {
        this.schooladdress = schooladdress;
    }

    public String getSchoolphoneNumber() {
        return schoolphoneNumber;
    }

    public void setSchoolphoneNumber(String schoolphoneNumber) {
        this.schoolphoneNumber = schoolphoneNumber;
    }

    public String getSchoolphone() {
        return schoolphone;
    }

    public void setSchoolphone(String schoolphone) {
        this.schoolphone = schoolphone;
    }

    public String getSchoolnum() {
        return schoolnum;
    }

    public void setSchoolnum(String schoolnum) {
        this.schoolnum = schoolnum;
    }

    public String getSchoolage() {
        return schoolage;
    }

    public void setSchoolage(String schoolage) {
        this.schoolage = schoolage;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    @Override
    public String toString() {
        return "SchoolRegistration{" +
                "id=" + id +
                ", schoolbabyname='" + schoolbabyname + '\'' +
                ", schooladdress='" + schooladdress + '\'' +
                ", schoolphoneNumber='" + schoolphoneNumber + '\'' +
                ", schoolphone='" + schoolphone + '\'' +
                ", schoolnum='" + schoolnum + '\'' +
                ", schoolage='" + schoolage + '\'' +
                ", campus='" + campus + '\'' +
                ", course='" + course + '\'' +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                ", relation='" + relation + '\'' +
                '}';
    }
}
