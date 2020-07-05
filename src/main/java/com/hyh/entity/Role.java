package com.hyh.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maxwisdom.parallel.utility.PageForm;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pe_role")
@Getter
@Setter
public class Role extends PageForm implements Serializable {
    private static final long serialVersionUID = 594829320797158219L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 角色名
     */
    private String name;
    /**
     * 说明
     */
    private String description;
    /**
     * 企业id
     */
    private String companyId;
    /*
    mappedBy忽略对中间表的维护
    *
    * */
   /* @JsonIgnore
    @ManyToMany(mappedBy="roles",fetch = FetchType.EAGER)
    private Set<User> users = new HashSet<User>(0);//角色与用户   多对多*/
    @ManyToMany(mappedBy = "roles")
    @Basic(fetch = FetchType.LAZY)
    @JsonIgnore//在get方法上加入此属性才有用 @JSONField(serialize = false)
    private Set<User> users = new HashSet<User>();//用户与角色   多对多


    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "pe_role_permission",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "permission_id", referencedColumnName = "id")})
    private Set<Permission> permissions = new HashSet<Permission>(0);//角色与模块  多对多

    @JSONField(serialize = false)//过滤不需要转换的属性
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }


    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }
}