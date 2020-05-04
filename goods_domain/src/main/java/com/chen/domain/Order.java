package com.chen.domain;

import java.util.List;

/**
 * 订单模块的实体类
 */
public class Order {
    private String oid;//主键
    private String ordertime;//下单时间
    private double total;//总金额
    private int status;//1.未付款，2.已付款但未发货，3.已发货但未确认收货，4确认收货交易成功，5.取消订单
    private String address;//收获地址
    private List<OrderItem> orderItemList;//订单条目
    private User user;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



}
