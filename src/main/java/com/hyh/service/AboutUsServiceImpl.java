package com.hyh.service;

import com.alibaba.fastjson.JSON;
import com.maxwisdom.parallel.dao.AboutUsDao;
import com.maxwisdom.parallel.entity.AboutUs;
import com.maxwisdom.parallel.utility.DataModel;
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
public class AboutUsServiceImpl implements AboutUsService{


    @Autowired
    private AboutUsDao aboutUsDao;

    @Autowired
    private FileService fileService;
    @Override
    public String findByshowAboutUs(AboutUs aboutUs) {
        Specification<AboutUs> spec = new Specification<AboutUs>() {
            public Predicate toPredicate(Root<AboutUs> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                list.add(criteriaBuilder.equal(root.get("showAboutUs").as(String.class), 1));//0false,1true

                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        aboutUs.setOrderDir("desc");
        aboutUs.setOrderStr("createTime");
        Sort sort;
        switch (aboutUs.getOrderDir()) {
            case "asc": {
                sort = new Sort(Sort.Direction.ASC, aboutUs.getOrderStr());
                break;
            }
            case "desc": {
                sort = new Sort(Sort.Direction.DESC, aboutUs.getOrderStr());
                break;
            }
            default: {
                sort = new Sort(Sort.Direction.DESC, aboutUs.getOrderStr());
            }
        }

        Pageable pageable = new PageRequest(aboutUs.getPageNum() - 1, aboutUs.getLength(), sort);
        Page<AboutUs> page = aboutUsDao.findAll(spec, pageable);
        return JSON.toJSONString(page);
    }

    @Override
    public String aboutUsSelect(AboutUs aboutUs){

        /*匿名内部类
         * Pageable,分页参数
         * 创建PageRequerst的过程中,需要调用他的构造方法传入两个参数
         * 1.当前查询的页数(从0开始)
         * 2.每页查询条数
         */
        System.out.println("进入招聘老师数据查询");
        Specification<AboutUs> spec = new Specification<AboutUs>() {
            public Predicate toPredicate(Root<AboutUs> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if (!StringUtils.isEmpty(aboutUs.getContent())) {
                    list.add(criteriaBuilder.like(root.get("content").as(String.class), "%"+aboutUs.getContent()+"%"));
                }
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        Sort sort;
        switch (aboutUs.getOrderDir()) {
            case "asc": {
                sort = new Sort(Sort.Direction.ASC, aboutUs.getOrderStr());
                break;
            }
            case "desc": {
                sort = new Sort(Sort.Direction.DESC, aboutUs.getOrderStr());
                break;
            }
            default: {
                sort = new Sort(Sort.Direction.DESC, aboutUs.getOrderStr());
            }
        }
        //安装报名日期倒序

        //Sort sort = new Sort(Sort.Direction.DESC, "age");
        Pageable pageable = new PageRequest(aboutUs.getPageNum() - 1, aboutUs.getLength(), sort);
//        List<AboutUs> Teachers= aboutUsDao.findAll();
        Page<AboutUs> page = aboutUsDao.findAll(spec, pageable);
        return JSON.toJSONString(DataModel.getSucDataModal(page.getContent(), (int) page.getTotalElements(), aboutUs.getDraw()));
    }

    @Override
    public String updateShowAbuotUs(AboutUs aboutUsnew) {
        try {
            AboutUs aboutUs = aboutUsDao.findById(aboutUsnew.getId()).get();
            aboutUs.setShowAboutUs(aboutUsnew.getShowAboutUs());

            AboutUs save = aboutUsDao.save(aboutUs);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "success";
    }

    @Override
    public String aboutUs_delete(Integer[] IDArray) {
        System.out.println("关于我们删除数据");
        AboutUs aboutUs;
        Map<String, Map> filemaperror = new HashMap<>();
        if (IDArray != null) {
            for (Integer id : IDArray) {
                aboutUs = aboutUsDao.findById(id.longValue()).get();//查询该对象
                boolean filetrue = true;
                //删除失败集合

                if (aboutUs != null) {
                    //记录错误信息
                    Map<String, String> filemap = new HashMap<>();
                    //删除个人照片
                    if (aboutUs.getP_yourself() != null ) {
                        if(fileService.filedelete(aboutUs.getP_yourself().getPath())){
                            //文件删除成功后执行
                            filemap.put("个人照片", "true");
                        }else {
                            filemap.put("个人照片", "false");
                            filetrue = false;
                        }
                    }
                    //如果全部删除成功则删除数据库记录
                    if (filetrue) {
                        aboutUsDao.delete(aboutUs);
                    } else {
                        filemaperror.put(aboutUs.getContent(), filemap);
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

    @Override
    public AboutUs findById(Long id) {
        return aboutUsDao.findById(id).get();
    }
}
