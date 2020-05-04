package com.chen.service;

import com.chen.domain.User;
import com.chen.utils.UserException;

/**
 * 用户模块的逻辑层接口
 */
public interface UserService {
    //校验用户名是否被注册
    User findByUsername(String username);
    //校验邮箱是否被注册
    User findByEmail(String email);

    //注册功能
    void regist(User user);

    //激活功能
    void activationCode(String uid) throws UserException;

    //登录功能
    User login(User fromUser);

    //修改密码
    void updatePassword(String uid,String oldPassword,String newPassword) throws UserException;
}
