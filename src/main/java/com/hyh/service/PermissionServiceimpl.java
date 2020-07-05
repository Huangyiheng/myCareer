package com.hyh.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.maxwisdom.parallel.dao.PermissionApiDao;
import com.maxwisdom.parallel.dao.PermissionDao;
import com.maxwisdom.parallel.dao.PermissionMenuDao;
import com.maxwisdom.parallel.dao.PermissionPointDao;
import com.maxwisdom.parallel.entity.Permission;
import com.maxwisdom.parallel.entity.PermissionApi;
import com.maxwisdom.parallel.entity.PermissionMenu;
import com.maxwisdom.parallel.entity.PermissionPoint;
import com.maxwisdom.parallel.utility.DataModel;
import com.maxwisdom.parallel.utility.Encrypt;
import com.maxwisdom.parallel.utility.UtilityHyh;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PermissionServiceimpl implements PermissionService {
    /*
     * 权限类型,1菜单,2功能,3API
     * */
    public static final String PY_MENU = "1";
    public static final String PY_POINT = "2";
    public static final String PY_API = "3";
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private PermissionMenuDao permissionMenuDao;
    @Autowired
    private PermissionPointDao permissionPointDao;
    @Autowired
    private PermissionApiDao permissionApiDao;

    public Permission save(Map<String, Object> map) throws Exception {

        //1.通过map构造permission对象
        //如果有id则是更新,则删除pid
        if(map.get("id").equals("")||map.get("id")==null){
            map.remove("id");
        }else{
            map.put("id",Long.parseLong(map.get("id").toString()));
        }
        Permission perm = UtilityHyh.mapToBean(map, Permission.class);

        //2.通过类型构造不同的资源对象(菜单,按钮,api)
        String type = perm.getType();
        Long type_ob_id;
        switch (type) {
            case PY_MENU:
                PermissionMenu menu = UtilityHyh.mapToBean(map, PermissionMenu.class);
                type_ob_id = permissionMenuDao.save(menu).getId();
                break;
            case PY_POINT:
                PermissionPoint point = UtilityHyh.mapToBean(map, PermissionPoint.class);
                type_ob_id = permissionPointDao.save(point).getId();
                ;
                break;
            case PY_API:
                PermissionApi api = UtilityHyh.mapToBean(map, PermissionApi.class);
                type_ob_id = permissionApiDao.save(api).getId();
                perm.setCode(Encrypt.md5(perm.getName(),api.getApiUrl()));
                break;
            default:
                throw new Exception("权限类型错误");

        }
        //3.保存
        perm.setType_ob_id(type_ob_id);

        permissionDao.save(perm);
        return null;
    }

    /*更新*/
    public Permission update(Map<String, Object> map) throws Exception {

        //1.通过map构造permission对象
        Permission perm = UtilityHyh.mapToBean(map, Permission.class);
        //2.通过传递的权限id查询
        Permission permission = permissionDao.findById(perm.getId()).get();
        permission.setName(perm.getName());
        permission.setCode(perm.getCode());
        permission.setDescribed(perm.getDescribed());
        permission.setEnVisible(perm.getEnVisible());
        //2.通过类型构造不同的资源对象(菜单,按钮,api)
        String type = perm.getType();
        switch (type) {
            case PY_MENU:
                PermissionMenu menu = UtilityHyh.mapToBean(map, PermissionMenu.class);
                menu.setId(perm.getId());
                permissionMenuDao.save(menu);
                break;
            case PY_POINT:
                PermissionPoint point = UtilityHyh.mapToBean(map, PermissionPoint.class);
                point.setId(perm.getId());
                permissionPointDao.save(point);
                break;
            case PY_API:
                PermissionApi api = UtilityHyh.mapToBean(map, PermissionApi.class);
                api.setId(perm.getId());
                permissionApiDao.save(api);
                break;
            default:
                throw new Exception("权限类型错误");

        }
        //3.保存
        permissionDao.save(perm);
        return null;
    }

    /*
     * 查询全部
     * type:查询全部权限列表type:0菜单+按钮(权限点),1:菜单,2:按钮(权限点),3:api接口
     * envisble :0查询所有saas平台的最高权限,1:查询企业权限
     * pid:父id
     * */
    public List<Permission> findAll(Map<String, Object> map) {
//1.需要查询条件
        Specification<Permission> spec = new Specification<Permission>() {
            /*
             * 拼接查询条件
             * */
            public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if(map!=null){


                //根据pid是否为空构查询条件
                if (!StringUtils.isEmpty((String) map.get("pid"))) {
                    list.add(criteriaBuilder.equal(root.get("pid").as(String.class), (String) map.get("pid")));
                }
                //根据enVisible构造查询条件
                if (!StringUtils.isEmpty((String) map.get("enVisible"))) {
                    list.add(criteriaBuilder.equal(root.get("enVisible").as(String.class), (String) map.get("enVisible")));

                }

                if (!StringUtils.isEmpty((String) map.get("type"))) {

                    String type = (String) map.get("type");
                    CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("type"));
                    if ("0".equals(type)) {
                        in.value(1).value(2);
                    } else {
                        in.value(Integer.parseInt(type));
                    }
                    list.add(in);

                }
                }

                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }

        };


        return permissionDao.findAll(spec);
    }

    /*
     * 根据id查询
     * //1.查询权限
     * //2.根据权限的类型查询资源
     * //3.构造map集合
     *
     * */
    public Map<String, Object> findById(Long id) {
        Permission perm = permissionDao.findById(id).get();
        String type = perm.getType();
        Object object = null;
        switch (type) {
            case PY_MENU:
                object = permissionMenuDao.findById(perm.getType_ob_id()).get();
                break;
            case PY_POINT:
                object = permissionPointDao.findById(perm.getType_ob_id()).get();
                break;
            case PY_API:
                object = permissionApiDao.findById(perm.getType_ob_id()).get();
                break;
            default:

        }




        Map<String, Object> map = UtilityHyh.beanToMap(object);

        map.put("id", perm.getId());
        map.put("name", perm.getName());
        map.put("type", perm.getType());
        map.put("code", perm.getCode());
        map.put("described", perm.getDescribed());
        map.put("pid", perm.getPid());
        map.put("enVisible", perm.getEnVisible());


        return map;
    }

    public String deleteById(Long id) {

//1.通过map构造permission对象
        //2.通过传递的权限id查询
        try {
            Permission permission = permissionDao.findById(id).get();
            permissionDao.delete(permission);


            //2.通过类型构造不同的资源对象(菜单,按钮,api)
            String type = permission.getType();
            switch (type) {
                case PY_MENU:
                    permissionMenuDao.deleteById(permission.getType_ob_id());
                    break;
                case PY_POINT:
                    permissionPointDao.deleteById(permission.getType_ob_id());
                    break;
                case PY_API:
                    permissionApiDao.deleteById(permission.getType_ob_id());
                    break;
                default:

            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }


        return "success";
    }

    /*删除菜单*/
    public String delte_menu(Long delte_id) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("pid", delte_id.toString());
            map.put("orderStr", "id");
            map.put("orderDir", "asc");
            map.put("pageNum", "1");
            map.put("length", "15");
            Page<Permission> re=findTypeAndPid(map);
            if (re!=null&&re.getContent().size()>0) {
                return "false";
            } else {

                return  deleteById(delte_id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }




    }










    /*返回树形节点数据*/

    public String getTreeListJson() {
        List<Permission> permissionList = permissionDao.findByType("1");
        String json = "";
        if (permissionList != null) {
            json = getTreeList(null, null, permissionList, null);
        }
        return json;
    }

    public String getTreeList(Permission kpi, List<Map<String, Object>> listmap, List<Permission> list,
                              HashMap<Integer, Integer> typeid) {
        String json = "";
        try {
            if (listmap == null || list == null) {
                // list.add(kpi);
                listmap = new ArrayList<Map<String, Object>>();
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
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("id", k.getId());
                        map.put("text", k.getName());
                        listmap.add(map);

                    }
                }
                getTreeList(kpi, listmap, list, typeid);
                // 如果最终结果集不为空
            } else if (listmap.size() > 0 && list.size() > 0) {
                // 循环最终结果集,所有根节点
                for (Map<String, Object> mp : listmap) {
                    // 创建一个子类集合对象
                    List<Map<String, Object>> childlist = new ArrayList<Map<String, Object>>();
                    // 循环所有结果集,查找有没有当前根目录的子节点
                    for (Permission k : list) {
                        String id = mp.get("id") + "";
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
                            Map<String, Object> m = new HashMap<String, Object>();
                            m.put("id", k.getId());
                            if (typeid == null || typeid.size() == 0) {

                                m.put("text", k.getName());

                            } else {

                                m.put("text", k.getName());
                            }


                            // 如果有则存入子类集合
                            childlist.add(m);
                        }
                    }
                    // 如果子类集合的对象不为空
                    if (childlist.size() > 0) {
                        List<String> sizelist = new ArrayList<String>();
                        sizelist.add(childlist.size() + "");
                        // 向最终结果集的一个对象添加一个子类集合
                        mp.put("nodes", childlist);
                        // 存入当前对象的长度
                        mp.put("tags", sizelist);

                        getTreeList(kpi, childlist, list, typeid);
                    }
                }
            }
            json = JSONArray.toJSON(listmap).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return json;
    }

    public Page<Permission> findTypeAndPid(Map<String, Object> map) {
//1.需要查询条件
        Specification<Permission> spec = new Specification<Permission>() {
            /*
             * 拼接查询条件
             * */
            public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                //根据pid是否为空构查询条件
                if (!StringUtils.isEmpty((String) map.get("pid"))) {
                    list.add(criteriaBuilder.equal(root.get("pid").as(String.class), (String) map.get("pid")));
                }
                //根据enVisible构造查询条件
                if (!StringUtils.isEmpty((String) map.get("enVisible"))) {
                    list.add(criteriaBuilder.equal(root.get("enVisible").as(String.class), (String) map.get("enVisible")));

                }

                if (!StringUtils.isEmpty((String) map.get("type"))) {

                    String type = (String) map.get("type");
                    CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("type"));
                    /*0则是查全部*/
                    if ("0".equals(type)) {
                        in.value(1).value(2);
                    } else {
                        in.value(Integer.parseInt(type));
                    }
                    list.add(in);
                }


                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }

        };


        Sort sort=null;
        switch ((String) map.get("orderDir")) {
            case "asc": {
                sort = new Sort(Sort.Direction.ASC, (String) map.get("orderStr"));
                break;
            }
            case "desc": {
                sort = new Sort(Sort.Direction.DESC, (String) map.get("orderStr"));
                break;
            }
            default: {
                sort = new Sort(Sort.Direction.DESC, (String) map.get("orderStr"));
            }
        }

        Pageable pageable = new PageRequest(Integer.parseInt((String) map.get("pageNum")) - 1, Integer.parseInt((String) map.get("length")), sort);
        Page<Permission> page = permissionDao.findAll(spec, pageable);
        return page;
    }

    public String getpoint(Map<String, Object> maps) {
        /*查询出该父菜单下的按钮*/

        Page<Permission> permissionList = findTypeAndPid(maps);
        /*遍历数据添加按钮信息*/
        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
        for (Permission perm : permissionList) {

            Long id = perm.getType_ob_id();
            String type = perm.getType();
            Object object = null;
            if (type == PY_MENU || type.equals(PY_MENU)) {
                object = permissionMenuDao.findById(id).get();
            } else if (type == PY_POINT || type.equals(PY_POINT)) {
                object = permissionPointDao.findById(id).get();
            } else if (type == PY_API || type.equals(PY_API)) {
                object = permissionApiDao.findById(id).get();
            } else {
                System.out.println("获取权限类型错误");
            }
            Map<String, Object> map = UtilityHyh.beanToMap(object);
            map.put("id",perm.getId());
            map.put("name", perm.getName());
            map.put("type", perm.getType());
            map.put("code", perm.getCode());
            map.put("described", perm.getDescribed());
            map.put("pid", perm.getPid());
            map.put("enVisible", perm.getEnVisible());
            listmap.add(map);
        }
        int draw = Integer.parseInt((String) maps.get("draw"));
        int recordsTotal = (int) permissionList.getTotalElements();
        return JSON.toJSONString(DataModel.getSucDataModal(listmap, recordsTotal, draw));

    }


}
