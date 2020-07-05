/**
 *作者:HYH
 *功能:
 *时间:2019年11月2日
 */
package com.hyh.dao;

import com.maxwisdom.parallel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


/**
 * @author HYH
 * spirngDataJpa接口的规范
 *JpaRepository<操作的实体类类型,实体类主键属性的类型,封装了基本crud操作
 *
 *JpaSpecificationExecutor<操作的实体类类型>,封装了复杂查询(例如分页)
 */
public interface UsersDao extends JpaRepository<User, Long>,JpaSpecificationExecutor<User> {

    public User findByUsernameAndPassword(String username, String password);

    public User findByUsername(String username);









}
