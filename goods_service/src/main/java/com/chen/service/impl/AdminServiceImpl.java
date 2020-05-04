package com.chen.service.impl;

import com.chen.dao.AdminDao;
import com.chen.domain.Admin;
import com.chen.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 管理员模块的Service 业务层接口实现类
 */
@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;

    /**
     * 管理员登录
     * @param a_name
     * @param a_password
     * @return
     */
    @Override
    public Admin login(String a_name, String a_password) {
        return adminDao.findByNameAndPassword(a_name,a_password);
    }
}
