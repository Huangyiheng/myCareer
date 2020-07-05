package com.hyh.service;

import com.maxwisdom.parallel.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {


    public User findByUsernameAndPassword(String username, String password);

    public User findByUsername(String username);

    public String getuserdata(User aduser) ;

    /**
     * 添加用户管理
     */
    public void addUser(User user);

    /**
     * 根据id删除用户
     */
    void deleteUserId(Long id);
    /**
     * 根据id查询用户
     */
    User findUserById(Long id);



    /**
     * 查询所有的用户信息
     * @return
     */
    List<User> findAllUser();

    /*根据id获取用户*/
    User findById(Long id);
    /*返回role节点*/
    String getUserTreeListJson(Long userid);


    String useradd(User user, String checkdata, MultipartFile photo_yourself) ;

    /*用户删除数据*/
    String user_delete(Integer[] idArray);

}
