/**
 *作者:HYH
 *功能:
 *时间:2019年11月2日
 */
package com.hyh.dao;

import com.maxwisdom.parallel.entity.JobWanted;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


/**
 * @author HYH
 * spirngDataJpa接口的规范
 *JpaRepository<操作的实体类类型,实体类主键属性的类型,封装了基本crud操作
 *
 *JpaSpecificationExecutor<操作的实体类类型>,封装了复杂查询(例如分页)
 */

    public interface JobWantedDao extends JpaRepository<JobWanted, Long>,JpaSpecificationExecutor<JobWanted> {

    public List<JobWanted> findByshowTeacher(int showTeacher);
    public JobWanted findByJobwantedId(long id);
    public JobWanted findByEMail(String email);








}
