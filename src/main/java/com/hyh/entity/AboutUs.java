package com.hyh.entity;

import com.maxwisdom.parallel.utility.PageForm;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体类
 */
@Entity
@Table(name = "about_us")
@Getter
@Setter
public class AboutUs extends PageForm implements Serializable {
    private static final long serialVersionUID = 4297464181093070302L;
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //时间
    private Date createTime;

    //内容
    private String content;


    private int showAboutUs;//首页展示外教,0false,1true


    @OneToOne(targetEntity = FileProperties.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "pYourself", referencedColumnName = "fileId")//name本实体类对应的数据库列名,referencedColumnName对应文件类关联的id
    private FileProperties p_yourself;//个人照片

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public int getShowAboutUs() {
        return showAboutUs;
    }

    public void setShowAboutUs(int showAboutUs) {
        this.showAboutUs = showAboutUs;
    }

    public FileProperties getP_yourself() {
        return p_yourself;
    }

    public void setP_yourself(FileProperties p_yourself) {
        this.p_yourself = p_yourself;
    }

    @Override
    public String toString() {
        return "AboutUs{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", content='" + content + '\'' +
                ", showAboutUs=" + showAboutUs +
                ", p_yourself=" + p_yourself +
                '}';
    }
}
