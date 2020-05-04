package com.chen.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.chen.domain.User;
import org.apache.ibatis.annotations.Update;
import org.springframework.security.core.parameters.P;


/**
 * 用户模块的持久层
 */
public interface UserDao {
    /**
     *  校验用户名是否被注册
     * @param username
     * @return
     */
    @Select("select * from user where username = #{username}")
    User findByUsername(@Param("username") String username);

    /**
     * 校验邮箱是否被注册
     * @param email
     * @return
     */
    @Select("select * from user where email = #{email}")
    User findByEmail(@Param("email") String email);

    /**
     * 添加一个用户
     * @param user
     */
    @Insert("insert into user(uid,username,password,email,status,activationCode) values(#{uid},#{username},#{password},#{email},#{status},#{activationCode})")
    void add(User user);
    /**
     * 修改用户的状态为激活状态
     */
    @Update("update  user set status = 1 where uid = #{uid}")
    void updateStatus(@Param("uid") String uid);

    /**
     * 根据用户uid查询用户
     * @param uid
     * @return
     */
    @Select("select * from user where uid = #{uid}")
    User findByUid(@Param("uid") String uid);

    /**
     * 根据用户名和密码查询用户
     * @param username
     * @param password
     * @return
     */
    @Select("select * from user where username = #{username} and password = #{password} ")
    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    /**
     * 修改密码
     * @param uid
     * @param newPassword
     */
    @Update("update user set password = #{newPassword} where uid = #{uid}")
    void updatePassword(@Param("uid") String uid, @Param("newPassword") String newPassword);

    /**
     * /根据uid和旧密码查询用户
     * @param uid
     * @param password
     * @return
     */
    @Select("select * from user where uid = #{uid} and password = #{password}")
    User findByUidAndPassword(@Param("uid") String uid, @Param("password") String password);
}
