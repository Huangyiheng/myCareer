package com.hyh.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.maxwisdom.parallel.dao.RoleDao;
import com.maxwisdom.parallel.dao.UsersDao;
import com.maxwisdom.parallel.entity.FileProperties;
import com.maxwisdom.parallel.entity.Permission;
import com.maxwisdom.parallel.entity.Role;
import com.maxwisdom.parallel.entity.User;
import com.maxwisdom.parallel.utility.DataModel;
import com.maxwisdom.parallel.utility.Encrypt;
import com.maxwisdom.parallel.utility.LayuiTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class UserServiceimpl implements UserService {
    @Autowired
    private UsersDao userdao;
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private FileService fileService;

    public User findByUsernameAndPassword(String username, String password) {
        return userdao.findByUsernameAndPassword(username, password);
    }

    public User findByUsername(String username) {
        return userdao.findByUsername(username);
    }

    public String getuserdata(User aduser) {
        /*匿名内部类
         * Pageable,分页参数
         * 创建PageRequerst的过程中,需要调用他的构造方法传入两个参数
         * 1.当前查询的页数(从0开始)
         * 2.每页查询条数
         */
        Specification<User> spec = new Specification<User>() {

            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate c1 = null;
                Predicate c2 = null;
                Predicate c3 = null;
                Predicate rt = null;
                //1.获取比较属性
               /* if (studr.getName() != null && !studr.getName().equals("")) {
                    //2.构造查询条件
                    c1 = cb.like(root.get("name"), "%" + studr.getName() + "%");//进行精准匹配(比较的属性,比较的属性取值)
                      cb.and(c1);
                }*/

              /*  if(studr.getCreatestart()!=null&&!studr.getCreatestart().equals("")){
                    Path<Object> name = root.get("name");
                    c2 = cb.lessThanOrEqualTo(root.get("createdTime"),studr.getCreatestart());//起始时间小于(数据库的值,前台传入的值)
                    cb.and(c2);
                }*/
               /* Path<Object> name = root.get("name");
                  c1 = cb.like(name.as(String.class), "%"+studr.getName()+"%");//进行精准匹配(比较的属性,比较的属性取值)
*/
                if (aduser.getName() == null) {//不修改将传入null进去
                    aduser.setName("");
                }
                c1 = cb.like(root.get("name"), "%" + aduser.getName() + "%");
                /*如果截止时间为空,则截止时间为当前日*/
                if (aduser.getCreateend() == null || aduser.getCreateend().equals("")) {
                    aduser.setCreateend(new Date());
                }
                c3 = cb.lessThanOrEqualTo(root.get("createdtime"), aduser.getCreateend());//起始时间大于(数据库的值,前台传入的值)

                /*如果起始时间为空则起始时间为1996年*/
                if (aduser.getCreatestart() == null || aduser.getCreatestart().equals("")) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date d = sdf.parse("1996-10-07");
                        aduser.setCreatestart(d);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                c2 = cb.greaterThanOrEqualTo(root.get("createdtime"), aduser.getCreatestart());//起始时间小于(数据库的值,前台传入的值)
                rt = cb.and(c1, c2, c3);
                return rt;
            }
        };
        /*添加排序
         * Sort.Direction.DESC:倒序
         * Sort.Direction.ASC:升序
         * 第二个参数:根据哪个属性排序
         * */
//多条件排序
        // Sort.Order order1 = new Sort.Order(Sort.Direction.ASC, "authStatus");
     /*   Sort.Order order2 = new Sort.Order(Sort.Direction.DESC, "createdTime");
        List<Sort.Order> list = new ArrayList();
       // list.add(order1);
        list.add(order2);
        Sort sort = Sort.by(list);*/

        Sort sort;
        switch (aduser.getOrderDir()) {
            case "asc": {
                sort = new Sort(Sort.Direction.ASC, aduser.getOrderStr());
                break;
            }
            case "desc": {
                sort = new Sort(Sort.Direction.DESC, aduser.getOrderStr());
                break;
            }
            default: {
                sort = new Sort(Sort.Direction.DESC, aduser.getOrderStr());
            }
        }


        //安装报名日期倒序
        //Sort sort = new Sort(Sort.Direction.DESC, "age");
        Pageable pageable = new PageRequest(aduser.getPageNum() - 1, aduser.getLength(), sort);
        Page<User> page = userdao.findAll(spec, pageable);


       /* System.out.println(page.getContent());//得到数据集合列表
        System.out.println(page.getTotalElements());//得到总条数
        System.out.println(page.getTotalPages());//得到总页数*/
        //返回datatables数据模型
        return JSON.toJSONString(DataModel.getSucDataModal(page.getContent(), (int) page.getTotalElements(), aduser.getDraw()));


    }


    /**
     * 保存用户的信息
     */
    @Override
    public void addUser(User user) {
        if (user != null) {
            String mdPassword = Encrypt.md5(user.getPassword(), user.getUsername());
            user.setPassword(mdPassword);
            //1.保存用户的信息
            User userDb = userdao.save(user);

          /*  //2.根据用户的id添加到角色
            Long id = userDb.getId();*/
            System.out.println("保存用户的信息，添加成功");
        }
    }


    /**
     * 根据id去删除用户的信息
     *
     * @param id
     */
    @Override
    public void deleteUserId(Long id) {

        if (id != null) {
            userdao.deleteById(id);
            System.out.println("根据用户的id删除用户执行");
        }
    }

    /**
     * 根据id查询到用户的信息
     *
     * @param id
     */
    @Override
    public User findUserById(Long id) {
        System.out.println("service中的id为:" + id);
        User user = userdao.findById(id).get();
        System.out.println(user.getName() + "----");
        return user;
    }


    /**
     * 查询所有的用户信息
     *
     * @return
     */
    @Override
    public List<User> findAllUser() {
        List<User> userList = userdao.findAll();

        return userList;
    }

    @Override
    public User findById(Long id) {
        return userdao.findById(id).get();
    }

    /*返回树形节点数据,根据角色id选中一有节点*/
    @Override
    public String getUserTreeListJson(Long userid) {
        List<Role> roleList = roleDao.findAll();
        HashMap<String, String> permap = new HashMap<>();
        //获取该用户已有id
        if (userid != null&&userid!=0) {
            User user = userdao.findById(userid).get();
            Set<Role> roles = user.getRoles();
            for (Role pe : roles) {
                permap.put(pe.getId().toString(), pe.getName());
            }
        }

        String json = "";
        if (roleList != null) {
            json = getUserTreeList(null, null, roleList, null, permap);
        }
        return json;
    }

    //    返回权限树形节点
    public String getUserTreeList(Permission kpi, List<LayuiTree> listmap, List<Role> list,
                                  HashMap<Integer, Integer> typeid, HashMap<String, String> permap) {
        String json = "";
        try {
            if (listmap == null || list == null) {
                // list.add(kpi);
                listmap = new ArrayList<LayuiTree>();
                // 循环所有分类集合,找出根节点
                for (Role k : list) {
                    // typeid为移动分类时所用,如果有值则不显示他自己和他子类的信息,直接跳过
                    if (typeid != null) {
                        if (typeid.get(k.getId()) != null) {
                            continue;
                        }
                    }
                    // 如果当前对象没有父级id,则为根目录,则进入,向结果集添加一个根节点对象
                    if (k.getCompanyId() == null || "".equals(k.getCompanyId()) || "0".equals(k.getCompanyId())) {
                        LayuiTree layuiTree = new LayuiTree();
                        layuiTree.setId(k.getId().toString());
                        layuiTree.setTitle(k.getName());
                        layuiTree.setField("name");

                        if (permap != null && permap.size() != 0) {//如果不等于空则已有权限
                            String idd = k.getId().toString();
                            String name = permap.get(idd);
                            if (name != null && !name.equals("")) {
                                layuiTree.setChecked(true);
                            } else {
                                layuiTree.setChecked(false);
                            }


                        }

                        listmap.add(layuiTree);

                    }
                }
                getUserTreeList(kpi, listmap, list, typeid, permap);
                // 如果最终结果集不为空
            } else if (listmap.size() > 0 && list.size() > 0) {
                // 循环最终结果集,所有根节点
                for (LayuiTree mp : listmap) {
                    // 创建一个子类集合对象
                    List<LayuiTree> childlist = new ArrayList<LayuiTree>();
                    // 循环所有结果集,查找有没有当前根目录的子节点
                    for (Role k : list) {
                        String id = mp.getId() + "";
                        String pid = k.getCompanyId() + "";

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
                                if (name != null && !name.equals("")) {
                                    layuiTree.setChecked(true);
                                } else {
                                    layuiTree.setChecked(false);
                                }


                            }

                            // 如果有则存入子类集合
                            childlist.add(layuiTree);
                        }
                    }
                    // 如果子类集合的对象不为空
                    if (childlist.size() > 0) {
                        // 向最终结果集的一个对象添加一个子类集合
                        mp.setChildren(childlist);
                        getUserTreeList(kpi, childlist, list, typeid, permap);
                    }
                }
            }
            json = JSONArray.toJSON(listmap).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return json;
    }


    @Override
    public String useradd(User usernew, String checkdata, MultipartFile photo_yourself) {
        try {
            Set<Role> roleList = new HashSet<>();
            User user;
            if(usernew.getId()!=null){
                user = userdao.findById(usernew.getId().longValue()).get();
            }else{
                user=usernew;
            }
            if (!checkdata.equals("")) {
                String[] permsidarry = checkdata.split(",");
                if (permsidarry != null && permsidarry.length > 0 && !permsidarry.equals("")) {
                    for (int i = 0; i < permsidarry.length; i++) {
                        Long id = Long.parseLong(permsidarry[i]);

                        Role role = roleDao.findById(id).get();

                        roleList.add(role);
                    }
                }
            }
            user.setRoles(roleList);
            String mdPassword = Encrypt.md5(usernew.getPassword(), usernew.getUsername());
            user.setPassword(mdPassword);
            if (photo_yourself != null) {
                if(user.getAvatarYourself()!=null){//删除原来的图片
                    fileService.filedelete(user.getAvatarYourself().getPath());
                }
                FileProperties photo_yourselfre = fileService.fileUpload(photo_yourself);//上传成功后判断返回结果存入数据库
                if (photo_yourselfre.getUploadstatus().equals("success")) {
                    user.setAvatarYourself(photo_yourselfre);
                }
            }

            user.setName(usernew.getName());
            user.setCreatedtime(new Date());
            return userdao.save(user).getId().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    /*用户删除数据*/
    @Override
    public String user_delete(Integer[] IDArray) {
        User user;
        Map<String, Map> filemaperror = new HashMap<>();
        if (IDArray != null) {
            for (Integer id : IDArray) {
                user = userdao.findById(id.longValue()).get();//查询该对象
                boolean filetrue = true;
                //删除失败集合
                if (user != null) {
                    //记录错误信息
                    Map<String, String> filemap = new HashMap<>();
                    //如果全部删除成功则删除数据库记录
                    if (filetrue) {
                        userdao.delete(user);
                    } else {
                        filemaperror.put(user.getName(), filemap);
                    }
                }
            }
            if (filemaperror.size() < 1) {
                return "success";
            } else {
                return JSON.toJSONString(filemaperror);
            }
        }
        return "null";
    }
}
