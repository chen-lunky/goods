package com.chen.service;

import com.chen.domain.Cartitem;

import java.util.List;

/**
 * 购物车模块的service业务层接口
 */
public interface CartitemService {
    //根据当前用户的uid去查询他购物车的购物信息
    List<Cartitem> findAll(String uid);
    //添加购物条目
    void add(Cartitem cartitem);
    //批量删除
    void batchDelete(String cartItemIds);
   //修改指定条目的数量
    Cartitem upateQuantity(String cartItemId, String quantity);
    //点击结算之后查看所勾选的条目
    List<Cartitem> showCartItems(String cartItemIds);
}
