package com.chen.service.impl;

import com.chen.dao.BookDao;
import com.chen.dao.OrderDao;
import com.chen.dao.OrderItemDao;
import com.chen.domain.Book;
import com.chen.domain.Order;
import com.chen.domain.OrderItem;
import com.chen.service.OrderService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单模块的service业务层接口实现类
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderItemDao orderItemDao;

    /**
     * 根据用户查询订单
     * @param uid
     * @return
     */
    @Override
    public List<Order> findOrderByUser(String uid,int page,int pageSize) {
        PageHelper.startPage(page,pageSize);
        List<Order> orderList = orderDao.findOrderByUser(uid);
        return orderList;
    }

    /**
     * 生成订单
     * @param order
     */
    @Override
    public void addOrder(Order order) {
        //调用orderDao添加订单
        orderDao.addOrder(order);
        List<OrderItem> orderItemList = order.getOrderItemList();
        //调用orderItemDao批量添加订单条目
        orderItemDao.addOrderItem(orderItemList);
    }

    /**
     * 根据oid查询订单
     * @param oid
     * @return
     */
    @Override
    public Order findByOid(String oid) {
        return orderDao.findByOid(oid);
    }

    /**
     * //根据修改指定订单的状态
     * @param oid
     * @param status
     */
    @Override
    public void updateStatus(String oid, int status) {
        orderDao.updateStatus(oid,status);

    }

    /**
     * 查询所有订单
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public List<Order> findAllOrder(int page, int pageSize) {
        PageHelper.startPage(page,pageSize);
        return orderDao.findAllOrder();
    }

    /**
     *根据status查询订单
     * @param status
     * @return
     */
    @Override
    public List<Order> findByStatus(String status,int page, int pageSize) {
        PageHelper.startPage(page,pageSize);
        return orderDao.findByStatus(Integer.parseInt(status));
    }

    /**
     * 按下单时间模糊查询
     * @param ordertime
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public List<Order> findByOrdertime(String ordertime, int page, int pageSize) {
        PageHelper.startPage(page,pageSize);
        return orderDao.findByOrdertime(ordertime);
    }
}
