/**
 * 作者:HYH
 * 功能:前台页面控制器
 * 时间:2020年3月18日
 */
package com.hyh.controller;

import com.alibaba.fastjson.JSON;
import com.maxwisdom.parallel.entity.Role;
import com.maxwisdom.parallel.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author HYH
 * ConfigurationProperties:使用注解获取application.yml中的
 * 属性,person为配置文件中的对象名称,自动对下面的name变量进行赋值,可以不写@Value("${name}")
 */
@Controller
@RequestMapping("/sys")
public class RolesController {
    @Autowired
    private RoleService roleService;

    @RequestMapping("/roleshome")
    public String roleshome() {
        System.out.println("角色管理页面");

        return "back/roles";
    }

    @RequestMapping("/getroledata")
    @ResponseBody
    public String getroledata(Role role) {

        return roleService.getroledata(role);

    }

    @RequestMapping("/rolesadd")
    @ResponseBody
    public String rolesadd(Role role, @RequestParam(value = "checkid", required = false) String checkid, HttpServletResponse response, HttpSession session) {
        return roleService.rolesadd(role,checkid);

    }

    /*分配角色*/
    @RequestMapping("/assignroles")
    @ResponseBody
    public String assignroles(@RequestParam Map<String, Object> map, HttpServletResponse response, HttpSession session) {
        //1.获取被分配的用户id
        Long userid = (Long) map.get("id");
        //2.获取角色id列表
        List<Long> roleid = (List<Long>) map.get("permids");
        //调用service层
        return roleService.assignRoles(userid, roleid);

    }

    /*返回role节点*/
    @RequestMapping("/roletreedata")
    @ResponseBody
    public String roletreedata(HttpServletResponse response, HttpSession session,
                               @RequestParam(value = "roleid", required = false,defaultValue = "0") Long roleid
    ) {

        return roleService.getRolesTreeListJson(roleid);
    }

    /*分配权限*/
    @RequestMapping("/assignperm")
    @ResponseBody
    public String assignperm(@RequestParam Map<String, Object> map, HttpServletResponse response, HttpSession session) {
        //1.获取被分配的角色id
        Long roleid = (Long) map.get("id");
        //2.获取权限id列表
        List<Long> permid = (List<Long>) map.get("permids");
        //调用service层
        return roleService.assignRoles(roleid, permid);

    }
    /*根据id获取角色*/
    @RequestMapping("/getrolebyid")
    @ResponseBody
    public String getrolebyid(Role role, HttpServletResponse response, HttpSession session) {

       return JSON.toJSONString(roleService.findById(role.getId()));

    }
    /*角色删除数据*/
    @RequestMapping("/role_delete")
    @ResponseBody
    public String role_delete(HttpServletResponse response, HttpSession session,
                                              @RequestParam(value = "IDArray[]") Integer[] IDArray
            /*  @RequestParam(value = "delete_id", required = false, defaultValue = "") String IDArray[]*/
    ) {
        System.out.println("招聘老师删除数据");
        // String[] IDArray = delete_id.split(",");
        return roleService.role_delete(IDArray);

    }


}
