package com.hyh.service;

import com.maxwisdom.parallel.entity.Joins;

import javax.servlet.http.HttpServletResponse;

public interface SchoolRegistrationService {
  public int save(Joins joins) ;

  String jionsData(Joins joins, HttpServletResponse response);

  //根据id进行查询
  Joins getRecruitmentbyid(long id);

  /*校区加盟删除数据*/
  String joins_delete(Integer[] idArray);
  /*校区加盟是否已读*/
    String updateShowJoins(Joins joins);
}
