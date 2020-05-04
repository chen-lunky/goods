package com.chen.service;

import com.chen.domain.Order;

import java.util.List;

/**
 * 订单模块的service业务层接口
 */
public interface OrderService {
    //根据用户查询订单
    List<Order> findOrderByUser(String uid,int page,int pageSize);
    //生成订单
    void addOrder(Order order);
    //根据oid查询订单
    Order findByOid(String oid);
    //根据修改指定订单的状态
    void updateStatus(String oid, int status);
    //查询所有订单
    List<Order> findAllOrder(int page, int pageSize);
    //根据status查询订单
    List<Order> findByStatus(String status,int page, int pageSize);
   //按下单时间模糊查询
    List<Order> findByOrdertime(String ordertime, int page, int pageSize);
}
