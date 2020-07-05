package com.hyh.service;

import com.maxwisdom.parallel.entity.Teacher;

public interface TeacherService {

public String teacherSelect(Teacher teacher);
    public String teacher_delete(Integer[] IDArray) ;
    public String updateShowTeacher(Teacher teachernew) ;
    public String findByshowTeacher(Teacher teacher) ;
    public Teacher findByTeacherdId(long id);

}
