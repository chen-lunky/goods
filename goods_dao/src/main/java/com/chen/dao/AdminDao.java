package com.chen.dao;

import com.chen.domain.Admin;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 管理员模块的dao持久层接口
 */
public interface AdminDao {
    /**
     * 管理员登录
     * @param a_name
     * @param a_password
     * @return
     */
    @Select("select * from admin where a_name = #{a_name} and a_password = #{a_password}")
    Admin findByNameAndPassword(@Param("a_name") String a_name, @Param("a_password") String a_password);
}
