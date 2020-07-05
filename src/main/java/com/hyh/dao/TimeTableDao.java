/**
 * 作者:HYH
 * 功能:
 * 时间:2019年11月2日
 */
package com.hyh.dao;

import com.maxwisdom.parallel.entity.TimeTableCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/**
 * @author HYH
 * spirngDataJpa接口的规范
 *JpaRepository<操作的实体类类型,实体类主键属性的类型,封装了基本crud操作
 *
 *JpaSpecificationExecutor<操作的实体类类型>,封装了复杂查询(例如分页)
 */
public interface TimeTableDao extends JpaRepository<TimeTableCourse, Long>, JpaSpecificationExecutor<TimeTableCourse> {
    @Query(value = "select * from timetable_course t where t.course_type= ?1 AND t.week=?2 AND t.timejobwanted=?3", nativeQuery = true)
    public TimeTableCourse selectbytypebyweekbyjobid(int courseType, int week, Long jobid);
    @Query(value = "select * from timetable_course t where t.course_type= ?1 AND t.week=?2 AND t.teachertime=?3", nativeQuery = true)
    public TimeTableCourse selectbytypebyweekbyteacherid(int courseType, int week, Long tea);

    @Query(value = "select * from timetable_course t where  t.timejobwanted=?1", nativeQuery = true)
    public List<TimeTableCourse> selectBytimejobwanted(Long jobid);
    @Query(value = "select * from timetable_course t where  t.teachertime=?1", nativeQuery = true)
    public List<TimeTableCourse> selectBytimeteacher(Long jobid);


/*
public TimeTableCourse findById(Long Id);
*/


}
