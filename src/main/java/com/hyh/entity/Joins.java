package com.hyh.entity;

import com.maxwisdom.parallel.utility.PageForm;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Table(name = "joins")
@Entity
public class Joins extends PageForm implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;//学校名称
    private String phone;//联系电话
    private String schoolphone;//联系人
    private String schoolnum;//在校人数
    private String age;//年龄
    private String campus;//校区
    private String remarks;//备注
    private String addresss;//学校地址
    private String relation;//是否已联系
    private Date createtime;
    private Date updatetime;
    private int showJoins;//首页展示外教,0false,1true

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAddresss() {
        return addresss;
    }

    public void setAddresss(String addresss) {
        this.addresss = addresss;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
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

    public int getShowJoins() {
        return showJoins;
    }

    public void setShowJoins(int showJoins) {
        this.showJoins = showJoins;
    }

    @Override
    public String toString() {
        return "Joins{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", schoolphone='" + schoolphone + '\'' +
                ", schoolnum='" + schoolnum + '\'' +
                ", age='" + age + '\'' +
                ", campus='" + campus + '\'' +
                ", remarks='" + remarks + '\'' +
                ", addresss='" + addresss + '\'' +
                ", relation='" + relation + '\'' +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                ", showJoins=" + showJoins +
                '}';
    }
}
