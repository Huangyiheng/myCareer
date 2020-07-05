package com.hyh.service;

import com.maxwisdom.parallel.entity.StudentRegistration;

public interface StudentRegistrationService {
  public int save(StudentRegistration studentRegistration) ;

    String recruitstudent_delete(Integer[] idArray);
  /*试听报名是否已读*/
    String updateShowStudent(StudentRegistration studentRegistration);

}
