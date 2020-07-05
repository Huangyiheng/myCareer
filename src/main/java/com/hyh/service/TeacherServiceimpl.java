package com.hyh.service;

import com.alibaba.fastjson.JSON;
import com.maxwisdom.parallel.dao.FilePropertiesDao;
import com.maxwisdom.parallel.dao.TeacherDao;
import com.maxwisdom.parallel.entity.Teacher;
import com.maxwisdom.parallel.utility.DataModel;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class TeacherServiceimpl implements TeacherService {
    @Autowired
    private FilePropertiesDao filedao;
    @Autowired
    private TeacherDao teacherDao;
    @Autowired
    private FileService fileService;

    public String teacherSelect(Teacher teacher){

        /*匿名内部类
         * Pageable,分页参数
         * 创建PageRequerst的过程中,需要调用他的构造方法传入两个参数
         * 1.当前查询的页数(从0开始)
         * 2.每页查询条数
         */
        System.out.println("进入招聘老师数据查询");
        Specification<Teacher> spec = new Specification<Teacher>() {

            public Predicate toPredicate(Root<Teacher> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                List<Predicate> list = new ArrayList<>();
                if (!StringUtils.isEmpty(teacher.getTeacherName())) {
                    list.add(criteriaBuilder.like(root.get("teacherName").as(String.class), "%" +teacher.getTeacherName()+"%"));
                }

                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };


        Sort sort;
        switch (teacher.getOrderDir()) {
            case "asc": {
                sort = new Sort(Sort.Direction.ASC, teacher.getOrderStr());
                break;
            }
            case "desc": {
                sort = new Sort(Sort.Direction.DESC, teacher.getOrderStr());
                break;
            }
            default: {
                sort = new Sort(Sort.Direction.DESC, teacher.getOrderStr());
            }
        }


        //安装报名日期倒序
        //Sort sort = new Sort(Sort.Direction.DESC, "age");
        Pageable pageable = new PageRequest(teacher.getPageNum() - 1, teacher.getLength(), sort);
        List<Teacher> Teachers= teacherDao.findAll();
        Page<Teacher> page = teacherDao.findAll(spec, pageable);


        //返回datatables数据模型
        return JSON.toJSONString(DataModel.getSucDataModal(page.getContent(), (int) page.getTotalElements(), teacher.getDraw()));
    }
    public String teacher_delete(Integer[] IDArray) {

        System.out.println("外教管理师删除数据");
        Teacher teacher;
        Map<String, Map> filemaperror = new HashMap<>();
        if (IDArray != null) {
            for (Integer id : IDArray) {
                teacher = teacherDao.findById(id.longValue()).get();//查询该对象
                boolean filetrue = true;
                //删除失败集合

                if (teacher != null) {
                    //记录错误信息
                    Map<String, String> filemap = new HashMap<>();
                    //删除个人照片
                    if (teacher.getPicture() != null ) {
                        if(fileService.filedelete(teacher.getPicture().getPath())){
                            //文件删除成功后执行
                            filemap.put("个人照片", "true");
                        }else {
                            filemap.put("个人照片", "false");
                            filetrue = false;
                        }
                    }

                    //删除个人视频
                    if (teacher.getPicture()!=null) {
                        if(fileService.filedelete(teacher.getVideo().getPath())){
                            //文件删除成功后执行
                            filemap.put("个人视频", "true");
                        }else {
                            filemap.put("个人视频", "false");
                            filetrue = false;
                        }
                    }

                    //如果全部删除成功则删除数据库记录
                    if (filetrue) {
                        teacherDao.delete(teacher);
                    } else {
                        filemaperror.put(teacher.getTeacherName(), filemap);
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
    public String updateShowTeacher(Teacher teachernew) {
        try {
            Teacher teacher = teacherDao.findById(teachernew.getTeacherdId()).get();
            teacher.setShowTeacher(teachernew.getShowTeacher());
            teacherDao.save(teacher);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "success";
    }
    public Teacher findByTeacherdId(long id){
       return teacherDao.findByTeacherdId(id);
    }


    public String findByshowTeacher(Teacher teacher) {
        Specification<Teacher> spec = new Specification<Teacher>() {
            public Predicate toPredicate(Root<Teacher> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                List<Predicate> list = new ArrayList<>();
                list.add(criteriaBuilder.equal(root.get("showTeacher").as(String.class), 1));//首页展示外教,0false,1true
                if (!teacher.getNationality().equals("0")){
                    list.add(criteriaBuilder.equal(root.get("nationality").as(String.class), teacher.getNationality()));//首页展示外教,0false,1true
                }
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        teacher.setOrderDir("desc");
        teacher.setOrderStr("createdtime");
        Sort sort;
        switch (teacher.getOrderDir()) {
            case "asc": {
                sort = new Sort(Sort.Direction.ASC, teacher.getOrderStr());
                break;
            }
            case "desc": {
                sort = new Sort(Sort.Direction.DESC, teacher.getOrderStr());
                break;
            }
            default: {
                sort = new Sort(Sort.Direction.DESC, teacher.getOrderStr());
            }
        }

        Pageable pageable = new PageRequest(teacher.getPageNum() - 1, teacher.getLength(), sort);
        Page<Teacher> page = teacherDao.findAll(spec, pageable);
        return JSON.toJSONString(page);
    }



}
