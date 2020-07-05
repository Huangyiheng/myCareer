package com.hyh.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 */
@Entity
@Table(name = "pe_permission_api")
@Getter
@Setter
public class PermissionApi  implements Serializable {
    private static final long serialVersionUID = -1803315043290784820L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 链接
     */
    private String apiUrl;
    /**
     * 请求类型
     * 1.post,2.get
     */
    private String apiMethod;
    /**
     * 权限等级，1为通用接口权限，2为需校验接口权限
     */
    private String apiLevel;



    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiMethod() {
        return apiMethod;
    }

    public void setApiMethod(String apiMethod) {
        this.apiMethod = apiMethod;
    }

    public String getApiLevel() {
        return apiLevel;
    }

    public void setApiLevel(String apiLevel) {
        this.apiLevel = apiLevel;
    }
}