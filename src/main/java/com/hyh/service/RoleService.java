package com.hyh.service;

import com.maxwisdom.parallel.entity.Role;

import java.util.List;

public interface RoleService {
    public String getroledata(Role role);
    public String rolesadd(Role role, String checkdata) ;
    public String assignRoles(Long userid, List<Long> roleIds) ;
    public String assignperm(Long userid, List<Long> roleIds) ;
    public String getRolesTreeListJson(Long roleid) ;
    public String role_delete(Integer[] IDArray) ;
    public Role findById(Long id);

}
