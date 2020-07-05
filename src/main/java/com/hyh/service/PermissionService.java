package com.hyh.service;

import com.maxwisdom.parallel.entity.Permission;

import java.util.List;
import java.util.Map;

public interface PermissionService {
    /*保存*/
    public Permission save(Map<String, Object> map) throws Exception;
    /*更新*/
    public Permission update(Map<String, Object> map) throws Exception;

    /*查询列表*/
    public List<Permission> findAll(Map<String, Object> map)throws Exception;
    /*根据id查询*/
    public Map<String, Object> findById(Long id);
    /*根据id删除*/
    public String deleteById(Long id);

    public String getTreeListJson() ;
    public String getpoint(Map<String, Object> map) ;
    public String delte_menu(Long delte_id) ;
}
