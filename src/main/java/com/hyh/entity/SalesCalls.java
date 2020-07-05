package com.hyh.entity;

import com.maxwisdom.parallel.utility.PageForm;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "SalesCalls")
@Getter
@Setter
public class SalesCalls extends PageForm implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long salesCallsid;
    private String name;//孩子姓名
    private Date dateOfBirth;//出生日期
    private String parentsNames;//家长姓名
    private String phone;//电话
    private String province;//省份
    private String city;//城市
    private String county;//区县
    private String villages;//乡镇
    private Date auditionTime;//试听课时间
    private String remark;//备注
    private int state;//状态:0.全部,1.沟通中,2无效,3.试听中,4签单,5续费.
    private Date signDate;//签单日期
    private BigDecimal money;//签单金额
    private Date courseExpiryDate;//课程到期时间
    private Date createDate;//创建时间
    private Date updateDate;//修改时间


    @ManyToOne(targetEntity = User.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher",referencedColumnName = "id")
    private User user;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }


    public Date getAuditionTime() {
        return auditionTime;
    }

    public void setAuditionTime(Date auditionTime) {
        this.auditionTime = auditionTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getParentsNames() {
        return parentsNames;
    }

    public void setParentsNames(String parentsNames) {
        this.parentsNames = parentsNames;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getVillages() {
        return villages;
    }

    public void setVillages(String villages) {
        this.villages = villages;
    }



    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Date getCourseExpiryDate() {
        return courseExpiryDate;
    }

    public void setCourseExpiryDate(Date courseExpiryDate) {
        this.courseExpiryDate = courseExpiryDate;
    }

    public Long getSalesCallsid() {
        return salesCallsid;
    }

    public void setSalesCallsid(Long salesCallsid) {
        this.salesCallsid = salesCallsid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
