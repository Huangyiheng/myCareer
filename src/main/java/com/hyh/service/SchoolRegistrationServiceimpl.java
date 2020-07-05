package com.hyh.service;

import com.alibaba.fastjson.JSON;
import com.maxwisdom.parallel.dao.JoinsDao;
import com.maxwisdom.parallel.entity.Joins;
import com.maxwisdom.parallel.utility.DataModel;
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
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class SchoolRegistrationServiceimpl implements SchoolRegistrationService {

    @Autowired
    private JoinsDao joinsDao;

    @Override
    public int save(Joins joins) {
        long id = joinsDao.save(joins).getId();
        int idInt = (int) id;
        return idInt;
    }

    /*学校入驻筛选分页查询*/
    @Override
    public String jionsData(Joins joins, HttpServletResponse response) {
        Specification<Joins> spec = new Specification<Joins>() {

//            javax.persistence.criteria.Predicate
            public Predicate toPredicate(Root<Joins> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate c1 = null;
                Predicate c2 = null;
                Predicate c3 = null;
                Predicate rt = null;

                if (joins.getName() == null) {//不修改将传入null进去
                    joins.setName("");
                }

                c1 = cb.like(root.get("name"), "%" + joins.getName() + "%");
                /*如果截止时间为空,则截止时间为当前日*/
                if (joins.getCreateend() == null || joins.getCreateend().equals("")) {
                    joins.setCreateend(new Date());
                }
                c3 = cb.lessThanOrEqualTo(root.get("createtime"), joins.getCreateend());//起始时间大于(数据库的值,前台传入的值)

                /*如果起始时间为空则起始时间为1996年*/
                if (joins.getCreatestart() == null || joins.getCreatestart().equals("")) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date d = sdf.parse("1996-10-07");
                        joins.setCreatestart(d);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                c2 = cb.greaterThanOrEqualTo(root.get("createtime"), joins.getCreatestart());//起始时间小于(数据库的值,前台传入的值)
                rt = cb.and(c1, c2, c3);
                return rt;
            }
        };


        Sort sort;
        switch (joins.getOrderDir()) {
            case "asc": {
                sort = new Sort(Sort.Direction.ASC, joins.getOrderStr());
                break;
            }
            case "desc": {
                sort = new Sort(Sort.Direction.DESC, joins.getOrderStr());
                break;
            }
            default: {
                sort = new Sort(Sort.Direction.DESC, joins.getOrderStr());
            }
        }


        //安装报名日期倒序
        //Sort sort = new Sort(Sort.Direction.DESC, "age");
        Pageable pageable = new PageRequest(joins.getPageNum() - 1, joins.getLength(), sort);
        Page<Joins> page = joinsDao.findAll(spec, pageable);


        //返回datatables数据模型
        return JSON.toJSONString(DataModel.getSucDataModal(page.getContent(), (int) page.getTotalElements(), joins.getDraw()));
    }

    @Override
    public Joins getRecruitmentbyid(long id) {
        return joinsDao.findById(id).get();
    }


    /*校区加盟删除数据*/
    @Override
    public String joins_delete(Integer[] IDArray) {
        Joins joins;
        Map<String, Map> filemaperror = new HashMap<>();
        if (IDArray != null) {
            for (Integer id : IDArray) {
                joins = joinsDao.findById(id.longValue()).get();//查询该对象
                boolean filetrue = true;
                //删除失败集合
                if (joins != null) {
                    //记录错误信息
                    Map<String, String> filemap = new HashMap<>();
                    //如果全部删除成功则删除数据库记录
                    if (filetrue) {
                        joinsDao.delete(joins);
                    } else {
                        filemaperror.put(joins.getName(), filemap);
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



    /*校区加盟是否已读*/
    public String updateShowJoins(Joins joinsnew) {
        try {
            Joins joins = joinsDao.findById(joinsnew.getId()).get();
            joins.setShowJoins(joinsnew.getShowJoins());
            joinsDao.save(joins);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "success";
    }
}
