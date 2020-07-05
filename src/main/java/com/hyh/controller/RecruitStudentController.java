/**
 * 作者:HYH
 * 功能:前台页面控制器
 * 时间:2020年3月18日
 */
package com.hyh.controller;

import com.alibaba.fastjson.JSON;
import com.maxwisdom.parallel.dao.StudentRegistrationDao;
import com.maxwisdom.parallel.entity.StudentRegistration;
import com.maxwisdom.parallel.service.StudentRegistrationService;
import com.maxwisdom.parallel.utility.DataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author HYH
 * ConfigurationProperties:使用注解获取application.yml中的
 * 属性,person为配置文件中的对象名称,自动对下面的name变量进行赋值,可以不写@Value("${name}")
 */
@RequestMapping("/recruitstudent")
@Controller
//@RequestMapping("/back")
//@ConfigurationProperties(prefix = "person")
public class RecruitStudentController {
    @Autowired
    private StudentRegistrationDao srdao;//学生管理

    @Autowired
    private StudentRegistrationService studentRegistrationService;
    @RequestMapping("/recruit_student")
    public String recruit_student() {
        System.out.println("进入招生管理页");
        return "back/recruit_student";
    }

    /*校区加盟删除数据*/
    @RequestMapping("/recruitstudent_delete")
    @ResponseBody
    public String recruitstudent_delete(HttpServletResponse response, HttpSession session,
                               @RequestParam(value = "IDArray[]") Integer[] IDArray
    ) {
        System.out.println(IDArray);
        return studentRegistrationService.recruitstudent_delete(IDArray);
    }


    @RequestMapping(value = "/recrui_student_data", produces = {"text/html;charset=UTF-8;", "application/json;"})
    @ResponseBody
    public String datatableData(StudentRegistration studr, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");//允许所有来源访问
        response.addHeader("Access-Control-Allow-Method:POST,GET", "*");//允许访问的方式


        /*匿名内部类
         * Pageable,分页参数
         * 创建PageRequerst的过程中,需要调用他的构造方法传入两个参数
         * 1.当前查询的页数(从0开始)
         * 2.每页查询条数
         */
        Specification<StudentRegistration> spec = new Specification<StudentRegistration>() {

            public Predicate toPredicate(Root<StudentRegistration> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
                    c2 = cb.lessThanOrEqualTo(root.get("createtime"),studr.getCreatestart());//起始时间小于(数据库的值,前台传入的值)
                    cb.and(c2);
                }*/
               /* Path<Object> name = root.get("name");
                  c1 = cb.like(name.as(String.class), "%"+studr.getName()+"%");//进行精准匹配(比较的属性,比较的属性取值)
*/
                if (studr.getName() == null) {//不修改将传入null进去
                    studr.setName("");
                }
                c1 = cb.like(root.get("name"), "%" + studr.getName() + "%");
                /*如果截止时间为空,则截止时间为当前日*/
                if (studr.getCreateend() == null || studr.getCreateend().equals("")) {
                    studr.setCreateend(new Date());
                }
                c3 = cb.lessThanOrEqualTo(root.get("createtime"), studr.getCreateend());//起始时间大于(数据库的值,前台传入的值)

                /*如果起始时间为空则起始时间为1996年*/
                if (studr.getCreatestart() == null || studr.getCreatestart().equals("")) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date d = sdf.parse("1996-10-07");
                        studr.setCreatestart(d);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                c2 = cb.greaterThanOrEqualTo(root.get("createtime"), studr.getCreatestart());//起始时间小于(数据库的值,前台传入的值)
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
     /*   Sort.Order order2 = new Sort.Order(Sort.Direction.DESC, "createtime");
        List<Sort.Order> list = new ArrayList();
       // list.add(order1);
        list.add(order2);
        Sort sort = Sort.by(list);*/

        Sort sort;
        switch (studr.getOrderDir()) {
            case "asc": {
                sort = new Sort(Sort.Direction.ASC, studr.getOrderStr());
                break;
            }
            case "desc": {
                sort = new Sort(Sort.Direction.DESC, studr.getOrderStr());
                break;
            }
            default: {
                sort = new Sort(Sort.Direction.DESC, studr.getOrderStr());
            }
        }


        //安装报名日期倒序
        //Sort sort = new Sort(Sort.Direction.DESC, "age");
        Pageable pageable = new PageRequest(studr.getPageNum() - 1, studr.getLength(), sort);
        Page<StudentRegistration> page = srdao.findAll(spec, pageable);

      /*  System.out.println(page.getContent());//得到数据集合列表
        System.out.println(page.getTotalElements());//得到总条数
        System.out.println(page.getTotalPages());//得到总页数*/
        //返回datatables数据模型
        return JSON.toJSONString(DataModel.getSucDataModal(page.getContent(), (int) page.getTotalElements(), studr.getDraw()));


    }


    /*试听报名是否已读*/
    @RequestMapping("/updateShowStudent")
    @ResponseBody
    public String updateShowStudent(StudentRegistration studentRegistration, HttpServletResponse response) {
        return studentRegistrationService.updateShowStudent(studentRegistration);
    }


}