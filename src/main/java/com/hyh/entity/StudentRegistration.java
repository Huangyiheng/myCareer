package com.hyh.entity;


import com.maxwisdom.parallel.utility.PageForm;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Table(name = "student_registration")
@Entity
public class StudentRegistration extends PageForm implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private String age;
    @Column(name = "gender")
    private String gender;
    @Column(name = "phone")
    private String phone;
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
    private int showStudent;//首页展示外教,0false,1true

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public int getShowStudent() {
        return showStudent;
    }

    public void setShowStudent(int showStudent) {
        this.showStudent = showStudent;
    }


    @Override
    public String toString() {
        return "StudentRegistration{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ", campus='" + campus + '\'' +
                ", course='" + course + '\'' +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                ", relation='" + relation + '\'' +
                ", showStudent=" + showStudent +
                '}';
    }
}
