/**
 * 作者:HYH
 * 功能:前台页面控制器
 * 时间:2020年3月18日
 */
package com.hyh.controller;

import com.alibaba.fastjson.JSON;
import com.maxwisdom.parallel.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author HYH
 * ConfigurationProperties:使用注解获取application.yml中的
 * 属性,person为配置文件中的对象名称,自动对下面的name变量进行赋值,可以不写@Value("${name}")
 */
@Controller
@RequestMapping("/sys")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/authority")
    public String authority() {
        System.out.println("权限管理页面");

        return "back/authority";
    }

    /*保存*/
    @RequestMapping("/permissionadd")
    @ResponseBody
    public String save(@RequestParam Map<String, Object> map) {

        try {
            permissionService.save(map);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

        return "sucees";
    }

    /*返回后台菜单结构数据*/
    @RequestMapping("/getTreeJson")
    @ResponseBody
    public String getTreeJson(@RequestParam Map<String, Object> map) {
            String jsondata="";
        try {
            jsondata=permissionService.getTreeListJson();
            System.out.println(jsondata);
            return jsondata;
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
    /*返回按钮权限*/
    @RequestMapping("/getpointorapi")
    @ResponseBody
    public String getpoint(@RequestParam Map<String, Object> map) {


        String jsondata="";
        try {
            jsondata=permissionService.getpoint(map);
            System.out.println(jsondata);
            return jsondata;
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

    }

    /*权限菜单更新获取数据*/
    @RequestMapping("/get_permission")
    @ResponseBody
    public String get_menu(HttpServletResponse response, HttpSession session,
                             @RequestParam(value = "delteid") Long delteid
            /*  @RequestParam(value = "delete_id", required = false, defaultValue = "") String IDArray[]*/
    ) {
        System.out.println("权限菜单更新");
        return  JSON.toJSONString( permissionService.findById(delteid));





    }

    /*权限菜单删除*/
    @RequestMapping("/delte_menu")
    @ResponseBody
    public String delte_menu(HttpServletResponse response, HttpSession session,
                             @RequestParam(value = "delteid") Long delteid
            /*  @RequestParam(value = "delete_id", required = false, defaultValue = "") String IDArray[]*/
    ) {
        System.out.println("权限菜单删除数据");
        return   permissionService.delte_menu(delteid);
    }

    /*权限删除*/
    @RequestMapping("/delte_point_api")
    @ResponseBody
    public String delte_menu(HttpServletResponse response, HttpSession session,
                                              @RequestParam(value = "IDArray[]") Long[] IDArray
            /*  @RequestParam(value = "delete_id", required = false, defaultValue = "") String IDArray[]*/
    ) {
        System.out.println("权限删除数据");

        Map<String, Map> filemaperror = new HashMap<>();
        if (IDArray != null) {
            for (Long id : IDArray) {
             if(permissionService.delte_menu(id).equals("false")  )   {
                 return "false";
             }
            }

        }
        return "success";
    }






    /*更新
    * 用@RequestParam请求接口时,URL是:http://www.test.com/user/getUserById?userId=1
       用@PathVariable请求接口时,URL是:http://www.test.com/user/getUserById/2
    * */
   /* @RequestMapping("/permission/{id}")
    public String update(
            @PathVariable(value = "id") String id,
            @RequestBody Map<String, Object> map
    ) throws Exception {
        map.put("id", id);

        permissionService.update(map);

        return "back/recruit_student";
    }*/


    /*查询列表*/
 /*   @RequestMapping("/permission")
    public String findAll( @RequestBody Map<String, Object> map) throws Exception {

        permissionService.findAll(map);

        return "back/recruit_student";
    }*/

    /*根据id查询*/
    /*@RequestMapping("/permission")
    public String findById(@PathVariable(value = "id")Long id) {
        Map map=permissionService.findById(id);

        return "back/recruit_student";
    }*/

    /*根据id删除*/
   /* @RequestMapping("/permission/{id}")
    public String deleteById(@PathVariable(value = "id")Long id) {

        permissionService.deleteById(id);
        return "back/recruit_student";
    }
*/


}
