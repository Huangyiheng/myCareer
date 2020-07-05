package com.hyh.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.maxwisdom.parallel.dao.PermissionDao;
import com.maxwisdom.parallel.dao.RoleDao;
import com.maxwisdom.parallel.dao.UsersDao;
import com.maxwisdom.parallel.entity.Permission;
import com.maxwisdom.parallel.entity.Role;
import com.maxwisdom.parallel.entity.User;
import com.maxwisdom.parallel.utility.DataModel;
import com.maxwisdom.parallel.utility.LayuiTree;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Service
@Transactional
public class RoleServiceimpl implements RoleService {
    public static final String PY_MENU = "1";
    public static final String PY_POINT = "2";
    public static final String PY_API = "3";
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UsersDao usersDao;
    @Autowired
    private PermissionDao permissionDao;

    /*返回role表格数据*/
    public String getroledata(Role role) {
//1.需要查询条件
        Specification<Role> spec = new Specification<Role>() {
            /*
             * 拼接查询条件
             * */
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                //根据pid是否为空构查询条件
                if (!StringUtils.isEmpty((String) role.getName())) {
                    list.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + role.getName() + "%"));
                }
                //根据enVisible构造查询条件


              /*  if (!StringUtils.isEmpty((String) map.get("type"))) {

                    String type = (String) map.get("type");
                    CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("type"));
                    *//*0则是查全部*//*
                    if ("0".equals(type)) {
                        in.value(1).value(2);
                    } else {
                        in.value(Integer.parseInt(type));
                    }
                    list.add(in);
                }*/


                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }

        };


        Sort sort;
        switch (role.getOrderDir()) {
            case "asc": {
                sort = new Sort(Sort.Direction.ASC, role.getOrderStr());
                break;
            }
            case "desc": {
                sort = new Sort(Sort.Direction.DESC, role.getOrderStr());
                break;
            }
            default: {
                sort = new Sort(Sort.Direction.DESC, role.getOrderStr());
            }
        }

        Pageable pageable = new PageRequest((role.getPageNum()) - 1, role.getLength(), sort);
        Page<Role> page = roleDao.findAll(spec, pageable);


        return JSON.toJSONString(DataModel.getSucDataModal(page.getContent(), (int) page.getTotalElements(), role.getDraw()));
    }

    public Role findById(Long id) {
        return roleDao.findById(id).get();
    }


    public String rolesadd(Role role, String checkdata) {
        try {
            Set<Permission> permissionList = new HashSet<>();
            if (!checkdata.equals("")) {
                String[] permsidarry = checkdata.split(",");
                if (permsidarry != null && permsidarry.length > 0 && !permsidarry.equals("")) {
                    for (int i = 0; i < permsidarry.length; i++) {
                        Long id = Long.parseLong(permsidarry[i]);
                        Permission permission = permissionDao.findById(id).get();
                        permissionList.add(permission);
                    }
                }
            }


            role.setPermissions(permissionList);
            return roleDao.save(role).getId().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

    }

    /*分配角色*/
    public String assignRoles(Long userid, List<Long> roleIds) {
        try {
            //1.根据id查询用户
            User user = usersDao.findById(userid).get();
            //2.设置用户的角色集合
            Set<Role> roles = new HashSet<>();
            for (Long roleid : roleIds) {
                Role role = roleDao.findById(roleid).get();
                roles.add(role);
            }
            //设置用户角色关系
            user.setRoles(roles);
            return usersDao.save(user).getId().toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

    }

    /*
     * 分配权限
     * */
    public String assignperm(Long roleid, List<Long> permid) {
        try {
            //1.获取分配的角色对象
            Role role = roleDao.findById(roleid).get();
            //2.构造角色的权限集合
            Set<Permission> permissionsset = new HashSet<>();
            for (Long perm : permid) {
                Permission permission = permissionDao.findById(perm).get();
                //根据父id和类型查询api权限列表
                List<Permission> apilist = permissionDao.findByTypeAndPid(PY_API, permission.getId().toString());
                permissionsset.addAll(apilist);//自定义赋予api权限
                permissionsset.add(permission);
            }
            //3.设置角色和权限的关系
            role.setPermissions(permissionsset);
            //4.更新角色
            return roleDao.save(role).getId().toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

    }

    /*删除角色*/
    public String role_delete(Integer[] IDArray) {

        System.out.println("角色删除数据");
        Role role;
        Map<String, Map> filemaperror = new HashMap<>();
        if (IDArray != null) {
            for (Integer id : IDArray) {
                role = roleDao.findById(id.longValue()).get();//查询该对象
                if (role != null) {
                    //删除失败集合
                    roleDao.delete(role);
                }

            }
            return "success";
        }
        return "null";
    }

    /*返回树形节点数据,根据角色id选中一有节点*/

    public String getRolesTreeListJson(Long roleid) {
        List<Permission> permissionList = permissionDao.findAll();
        HashMap<String, String> permap = new HashMap<>();
        //获取该角色已有id
        if (roleid != null && roleid != 0) {
            Role role = roleDao.findById(roleid).get();
            Set<Permission> rolepermissionList = role.getPermissions();


            for (Permission pe : rolepermissionList) {
                permap.put(pe.getId().toString(), pe.getName());

            }
        }

        String json = "";
        if (permissionList != null) {
            json = getRolesTreeList(null, null, permissionList, null, permap);
        }

      /*  json="[{\n" +
                "    title: '一级1'\n" +
                "    ,id: 1\n" +
                "    ,field: 'name1'\n" +
                "    ,checked: true\n" +
                "    ,spread: true}]";*/
        // json="[{\"field\":\"name\",\"checked\":true,\"disabled\":false,\"id\":\"40\",\"title\":\"测试\",\"spread\":false,\"children\":[{\"field\":\"name\",\"checked\":false,\"disabled\":false,\"id\":\"31\",\"title\":\"试听报名home\",\"spread\":false},{\"field\":\"name\",\"checked\":false,\"disabled\":false,\"id\":\"31\",\"title\":\"试听报名home\",\"spread\":false}]}]";
        System.out.println(json);
        return json;
    }


    /*返回权限树形节点*/
    public String getRolesTreeList(Permission kpi, List<LayuiTree> listmap, List<Permission> list,
                                   HashMap<Integer, Integer> typeid, HashMap<String, String> permap) {
        String json = "";
        try {
            if (listmap == null || list == null) {
                // list.add(kpi);
                listmap = new ArrayList<LayuiTree>();
                // 循环所有分类集合,找出根节点
                for (Permission k : list) {
                    // typeid为移动分类时所用,如果有值则不显示他自己和他子类的信息,直接跳过
                    if (typeid != null) {
                        if (typeid.get(k.getId()) != null) {
                            continue;
                        }
                    }

                    // 如果当前对象没有父级id,则为根目录,则进入,向结果集添加一个根节点对象
                    if (k.getPid() == null || "".equals(k.getPid()) || "0".equals(k.getPid())) {
                        LayuiTree layuiTree = new LayuiTree();
                        layuiTree.setId(k.getId().toString());
                        layuiTree.setTitle(k.getName());
                        layuiTree.setField("name");

                        if (permap != null && permap.size() != 0) {//如果不等于空则已有权限
                            String idd = k.getId().toString();
                            String name = permap.get(idd);
                            if (name != null && !name.equals("")) {//根目录不需要选中,当有子节点被选择时父目录会被插件自动选中
                                // layuiTree.setChecked(true);
                                layuiTree.setChecked(false);
                            } else {
                                layuiTree.setChecked(false);
                            }
                        }
                        listmap.add(layuiTree);

                    }
                }
                getRolesTreeList(kpi, listmap, list, typeid, permap);
                // 如果最终结果集不为空
            } else if (listmap.size() > 0 && list.size() > 0) {
                // 循环最终结果集,所有根节点
                for (LayuiTree mp : listmap) {
                    // 创建一个子类集合对象
                    List<LayuiTree> childlist = new ArrayList<LayuiTree>();
                    // 循环所有结果集,查找有没有当前根目录的子节点
                    for (Permission k : list) {
                        String id = mp.getId() + "";
                        String pid = k.getPid() + "";

                        // typeid为移动分类时所用,如果有值则不显示他自己和他子类的信息,直接跳过
                        if (typeid != null) {
                            if (typeid.get(k.getId()) != null) {
                                continue;
                            }
                        }
                        // 判断有没有最终结果集的子节点
                        if (id.equals(pid)) {
                            // 如果有则,将信息存入map对象,再存入子类集合
                            LayuiTree layuiTree = new LayuiTree();
                            layuiTree.setId(k.getId().toString());
                            layuiTree.setTitle(k.getName());
                            layuiTree.setField("name");
                            if (permap != null && permap.size() != 0) {//如果不等于空则已有权限
                                String idd = k.getId().toString();
                                String name = permap.get(idd);
                                boolean seletct = true;
                                for (Permission k1 : list) {//判断当前节点还有没有子节点,有则不设为true,没有子节点设为true
                                    String pid1 = k1.getPid() + "";
                                    // 判断有没有最终结果集的子节点
                                    if (idd.equals(pid1)) {
                                        seletct=false;
                                    }
                                }

                                if (name != null && !name.equals("")) {
                                    layuiTree.setChecked(seletct);
                                } else {
                                    layuiTree.setChecked(false);
                                }
                            }

                            // 如果有则存入子类集合
                            childlist.add(layuiTree);
                        } else {


                        }
                    }
                    // 如果子类集合的对象不为空
                    if (childlist.size() > 0) {
                        // 向最终结果集的一个对象添加一个子类集合
                        mp.setChildren(childlist);
                        getRolesTreeList(kpi, childlist, list, typeid, permap);
                    }
                }
            }
            json = JSONArray.toJSON(listmap).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }


}
