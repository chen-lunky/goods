package com.chen.service;

import com.chen.domain.Admin;

/**
 * 管理员模块的Service 业务层接口
 */
public interface AdminService {
    //管理员登录
    Admin login(String a_name, String a_password);

}
