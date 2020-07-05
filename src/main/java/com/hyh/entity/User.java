package com.hyh.entity;

import com.maxwisdom.parallel.utility.PageForm;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户实体类
 */
@Entity
@Table(name = "bs_user")
@Getter
@Setter
public class User extends PageForm implements Serializable {
    private static final long serialVersionUID = 4297464181093070302L;
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "avatar")
    private String avatar;
    @Column(name = "remember_token")
    private String remember_token;
    @Column(name = "createdtime")
    private Date createdtime;
    @Column(name = "updatedtime")
    private Date updatedtime;

    /**
     * 部门ID
     */
    private String departmentId;
    /*配置用户到教师的一对一关系*/

    @OneToOne(cascade=CascadeType.ALL)//People是关系的维护端，当删除 people，会级联删除 address
    @JoinColumn(name = "jobWantedid", referencedColumnName = "jobwantedId")//name本实体类对应的数据库列名,referencedColumnName对应文件类关联的id
    private JobWanted jobWanted;

    @OneToOne(targetEntity = FileProperties.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "avatarYourself", referencedColumnName = "fileId")//name本实体类对应的数据库列名,referencedColumnName对应文件类关联的id
    private FileProperties avatarYourself;//头像相片

    /*配置用户到角色的多对多关系
     * 配置多对多映射关系
     * 	1.声明关系的配置
     * 	2.配置中间表(包含两个外键)
     *  @ManyToMany:对多对的关系
     *  @JoinTable:
     *  	name:中间表的名称
     *  	joinColumns:配置当前对象在中间表的外键
     *  @JoinColumn的数组:
     *  	name:外键名
     *  	referencedColumnName:参照的主表的主键名
     *  @JsonIgnore忽略对json转换,否则转换roles,roles里又包含user会死循环
     * fetch = FetchType.EAGER
     * 如果是EAGER，那么表示取出这条数据时，它关联的数据也同时取出放入内存中
     * 如果是LAZY，那么取出这条数据时，它关联的数据并不取出来，在同一个session中，什么时候要用，就什么时候取(再次访问数据库)。
     * */
    //joinColumns:当前对象在中间表的外键
    //inverseJoinColumns:对方对象在中间表的外键
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "pe_user_role", joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private Set<Role> roles = new HashSet<Role>();//用户与角色   多对多

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRemember_token() {
        return remember_token;
    }

    public void setRemember_token(String remember_token) {
        this.remember_token = remember_token;
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

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public JobWanted getJobWanted() {
        return jobWanted;
    }

    public void setJobWanted(JobWanted jobWanted) {
        this.jobWanted = jobWanted;
    }

    public FileProperties getAvatarYourself() {
        return avatarYourself;
    }

    public void setAvatarYourself(FileProperties avatarYourself) {
        this.avatarYourself = avatarYourself;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
