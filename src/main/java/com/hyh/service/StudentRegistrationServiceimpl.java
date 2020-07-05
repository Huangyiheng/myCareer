package com.hyh.service;

import com.alibaba.fastjson.JSON;
import com.maxwisdom.parallel.dao.StudentRegistrationDao;
import com.maxwisdom.parallel.entity.StudentRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class StudentRegistrationServiceimpl implements StudentRegistrationService {

    @Autowired
    private StudentRegistrationDao registrationDao;

    public int save(StudentRegistration studentRegistration) {
        long id = registrationDao.save(studentRegistration).getId();
        int idInt = (int) id;
        return idInt;
    }

    /*校区加盟删除数据*/
    @Override
    public String recruitstudent_delete(Integer[] IDArray) {
        StudentRegistration studentRegistration;
        Map<String, Map> filemaperror = new HashMap<>();
        if (IDArray != null) {
            for (Integer id : IDArray) {
                studentRegistration = registrationDao.findById(id.longValue()).get();//查询该对象
                boolean filetrue = true;
                //删除失败集合
                if (studentRegistration != null) {
                    //记录错误信息
                    Map<String, String> filemap = new HashMap<>();
                    //如果全部删除成功则删除数据库记录
                    if (filetrue) {
                        registrationDao.delete(studentRegistration);
                    } else {
                        filemaperror.put(studentRegistration.getName(), filemap);
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

    /*试听报名是否已读*/
    public String updateShowStudent(StudentRegistration studentRegistrationnew) {
        try {
            StudentRegistration studentRegistration = registrationDao.findById(studentRegistrationnew.getId()).get();
            studentRegistration.setShowStudent(studentRegistrationnew.getShowStudent());
            registrationDao.save(studentRegistration);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "success";
    }
}
