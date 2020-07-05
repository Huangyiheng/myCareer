package com.hyh.entity;



import com.maxwisdom.parallel.utility.PageForm;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.persistence.*;
import java.util.Date;

//此注解对应application配置文件中写的参数

@ConfigurationProperties(prefix = "file", ignoreUnknownFields = true)
@EnableConfigurationProperties(FileProperties.class)
@Table(name = "fileproperties")
@Entity
public class FileProperties extends PageForm {
    private String uploaddir;//文件上传路径,从配置文件中获取
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long fileId;//文件原名
    private String name;//文件原名
    private String skeyname;//文件存储名称
    private String path;//文件路径
    private String type;//文件类型
    private Date createdtime;//创建时间
    private Date updatedtime;//更新时间

    private String deletetype;//删除状态:1.已删除,2.未删除
    private String lenth;//文件大小
    private String cuser;//关联用户id
    private String qiuzhiuser;//关联教师求职id
    private String euser;//关联修改用户id
    private String uploadstatus;//上传状态

    @OneToOne(targetEntity = JobWanted.class,cascade=CascadeType.ALL)
    @JoinColumn(name = "Jobwanted",referencedColumnName = "jobwantedId")//name本实体类对应的数据库列名,referencedColumnName对应老师求职类关联的id
    private JobWanted Jobwanted;//老师求职


    public String getUploadstatus() {
        return uploadstatus;
    }

    public void setUploadstatus(String uploadstatus) {
        this.uploadstatus = uploadstatus;
    }

    public String getUploaddir() {
        return uploaddir;
    }

    public void setUploaddir(String uploaddir) {
        this.uploaddir = uploaddir;
    }

    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkeyname() {
        return skeyname;
    }

    public void setSkeyname(String skeyname) {
        this.skeyname = skeyname;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(Date createdtime) {
        this.createdtime = createdtime;
    }

    public Date getUpdatedtime() {
        return updatedtime;
    }

    public void setUpdatedtime(Date updatedtime) {
        this.updatedtime = updatedtime;
    }

    public String getDeletetype() {
        return deletetype;
    }

    public void setDeletetype(String deletetype) {
        this.deletetype = deletetype;
    }

    public String getLenth() {
        return lenth;
    }

    public void setLenth(String lenth) {
        this.lenth = lenth;
    }

    public String getCuser() {
        return cuser;
    }

    public void setCuser(String cuser) {
        this.cuser = cuser;
    }

    public String getQiuzhiuser() {
        return qiuzhiuser;
    }

    public void setQiuzhiuser(String qiuzhiuser) {
        this.qiuzhiuser = qiuzhiuser;
    }

    public String getEuser() {
        return euser;
    }

    public void setEuser(String euser) {
        this.euser = euser;
    }
}