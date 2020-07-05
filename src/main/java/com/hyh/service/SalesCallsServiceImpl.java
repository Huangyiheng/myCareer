package com.hyh.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.maxwisdom.parallel.dao.SalesCallsDao;
import com.maxwisdom.parallel.dao.UsersDao;
import com.maxwisdom.parallel.entity.*;
import com.maxwisdom.parallel.entity.SalesCalls;
import com.maxwisdom.parallel.utility.DataModel;
import com.maxwisdom.parallel.utility.ExcelUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
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
import java.util.*;

@Service
@Transactional
public class SalesCallsServiceImpl implements SalesCallsService {
    @Autowired
    private SalesCallsDao salesCallsDao;
    @Autowired
    private UsersDao usersDao;
    @Autowired
    private UserService userService;

    /*返回数据*/
    public String getsalescallsDate(SalesCalls salesCalls) {
        /*根据当前登录用户返回数据*/
        if(salesCalls.getUser()==null){
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            user = userService.findByUsernameAndPassword(user.getUsername(), user.getPassword());
            salesCalls.setUser(user);
        }

        /*匿名内部类
         * Pageable,分页参数
         * 创建PageRequerst的过程中,需要调用他的构造方法传入两个参数
         * 1.当前查询的页数(从0开始)
         * 2.每页查询条数
         */
        System.out.println("进入招聘老师数据查询");
        Specification<SalesCalls> spec = new Specification<SalesCalls>() {

            public Predicate toPredicate(Root<SalesCalls> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                List<Predicate> list = new ArrayList<>();
                if (!StringUtils.isEmpty(salesCalls.getName())) {
                    list.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + salesCalls.getName() + "%"));
                }

                if (!StringUtils.isEmpty(salesCalls.getParentsNames())) {
                    list.add(criteriaBuilder.like(root.get("parentsNames").as(String.class), "%" + salesCalls.getParentsNames() + "%"));
                }
                if (!StringUtils.isEmpty(salesCalls.getPhone())) {
                    list.add(criteriaBuilder.like(root.get("phone").as(String.class), "%" + salesCalls.getPhone() + "%"));
                }
                if (salesCalls.getState() != 0) {
                    list.add(criteriaBuilder.equal(root.get("state").as(int.class), salesCalls.getState()));
                }
                if (salesCalls.getUser() != null&&salesCalls.getUser().getId()!=0) {
                    list.add(criteriaBuilder.equal(root.get("user").as(User.class), salesCalls.getUser()));
                }


                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };


        Sort sort;
        switch (salesCalls.getOrderDir()) {
            case "asc": {
                sort = new Sort(Sort.Direction.ASC, salesCalls.getOrderStr());
                break;
            }
            case "desc": {
                sort = new Sort(Sort.Direction.DESC, salesCalls.getOrderStr());
                break;
            }
            default: {
                sort = new Sort(Sort.Direction.DESC, salesCalls.getOrderStr());
            }
        }


        //安装报名日期倒序
        //Sort sort = new Sort(Sort.Direction.DESC, "age");
        Pageable pageable = new PageRequest(salesCalls.getPageNum() - 1, salesCalls.getLength(), sort);
        Page<SalesCalls> page = salesCallsDao.findAll(spec, pageable);
        //返回datatables数据模型
        return JSON.toJSONString(DataModel.getSucDataModal(page.getContent(), (int) page.getTotalElements(), salesCalls.getDraw()), SerializerFeature.DisableCircularReferenceDetect);


    }

    public String salescallsAdd(SalesCalls salesCalls) {


        try {


            SalesCalls SalesCallslast;
            if (salesCalls != null) {
                if (salesCalls.getSalesCallsid() != null) {

                    SalesCallslast = salesCallsDao.findBysalesCallsid(salesCalls.getSalesCallsid());
                    salesCalls.setUser(SalesCallslast.getUser());
                    salesCalls.setCreateDate(SalesCallslast.getCreateDate());
                    salesCalls.setUpdateDate(new Date());
                } else {
                    salesCalls.setCreateDate(new Date());
                }

            } else {
                return "error";
            }

            return salesCallsDao.save(salesCalls).getSalesCallsid().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

    }


    public String salescallsdelete(Integer[] IDArray) {
        System.out.println("招聘老师删除数据");
        SalesCalls salesCalls;
        Map<String, Map> filemaperror = new HashMap<>();
        if (IDArray != null) {
            for (Integer id : IDArray) {
                salesCalls = salesCallsDao.findBysalesCallsid(id.longValue());//查询该对象
                boolean filetrue = true;
                //删除失败集合
                if (salesCalls != null) {
                    //记录错误信息
                    Map<String, String> filemap = new HashMap<>();
                    //如果全部删除成功则删除数据库记录
                    if (filetrue) {
                        salesCallsDao.delete(salesCalls);
                    } else {
                        filemaperror.put(salesCalls.getName(), filemap);
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

    /*批量导入学生数据*/
    public String importexcl(MultipartFile importexcl) {
        HashMap<String, Object> hashMap = ExcelUtils.readExcel(importexcl);
        if (hashMap.get("error") != null) {//如果有错误信息则返回错误信息
            return hashMap.get("error").toString();
        } else {
            try {
                List<SalesCalls> dataList = (List<SalesCalls>) hashMap.get("contend");
                for (SalesCalls salesCalls : dataList) {
                    salesCallsDao.save(salesCalls);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "error";
            }
        }

        return "success";
    }


    public String allocation(Integer[] IDArray, Long user) {
        System.out.println("预约试听分配数据");
        SalesCalls salesCalls;

        Map<String, Map> filemaperror = new HashMap<>();
        User user1 = usersDao.findById(user).get();
        if (user1 != null) {
            if (IDArray != null) {
                for (Integer id : IDArray) {
                    salesCalls = salesCallsDao.findBysalesCallsid(id.longValue());//查询该对象

                    boolean filetrue = true;
                    //删除失败集合
                    if (salesCalls != null) {
                        //记录错误信息
                        Map<String, String> filemap = new HashMap<>();
                        //如果全部删除成功则删除数据库记录
                        if (filetrue) {
                            salesCalls.setUser(user1);
                            salesCallsDao.save(salesCalls);
                        } else {
                            filemaperror.put(salesCalls.getName(), filemap);
                        }
                    }
                }
                if (filemaperror.size() < 1) {
                    return "success";
                } else {
                    return JSON.toJSONString(filemaperror);
                }
            }
        } else {
            return "error";
        }

        return "";
    }


}
