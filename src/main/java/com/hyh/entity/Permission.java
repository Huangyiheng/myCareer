package com.hyh.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "pe_permission")
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert(true)
@DynamicUpdate(true)
public class Permission implements Serializable {
    private static final long serialVersionUID = -4990810027542971546L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 权限类型 1为菜单 2为功能按钮 3为API接口路径
     */
    private String type;
/*所对应的不同类型的具体数据的id*/
    private Long type_ob_id;
    private String code;

    /**
     * 权限描述
     */
    private String described;
/*
* 一个权限下的具体权限
* 菜单--多个按钮
* 菜单--api权限
* 菜单
* */
    private String pid;

    private String enVisible;

    public Long getType_ob_id() {
        return type_ob_id;
    }

    public void setType_ob_id(Long type_ob_id) {
        this.type_ob_id = type_ob_id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescribed() {
        return described;
    }

    public void setDescribed(String described) {
        this.described = described;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getEnVisible() {
        return enVisible;
    }
    public void setEnVisible(String enVisible) {
        this.enVisible = enVisible;
    }

    public Permission(String name, String type, String code, String description) {
        this.name = name;
        this.type = type;
        this.code = code;
        this.described = description;
    }


}