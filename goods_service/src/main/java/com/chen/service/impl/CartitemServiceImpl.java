package com.chen.service.impl;

import com.chen.dao.CartitemDao;
import com.chen.domain.Cartitem;
import com.chen.service.CartitemService;
import com.chen.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车模块的service业务层接口实现类
 */
@Service
@Transactional
public class CartitemServiceImpl implements CartitemService {
    @Autowired
    private CartitemDao cartitemDao;

    /**
     * 根据当前用户的uid去查询他购物车的购物信息
     * @param uid
     * @return
     */
    @Override
    public List<Cartitem> findAll(String uid) {
        return cartitemDao.findAllByUid(uid);
    }

    /**
     * 添加购物条目
     * @param cartitem
     */
    @Override
    public void add(Cartitem cartitem) {
        //先根据用户uid和图书bid查询购物条目是否存在
        Cartitem _cartitem = cartitemDao.findByUidAndBid(cartitem.getUser().getUid(),cartitem.getBook().getBid());
        //如果存在，就修改条目的数量
        if(_cartitem != null){
            //条目的数量等于传入参数的数量+数据库原本的数量
            int quantity = cartitem.getQuantity()+_cartitem.getQuantity();
            cartitemDao.updateQuantity(quantity,_cartitem.getCartItemId());
        }else{//不存在。
            //设置购物条目的cartItemId
            cartitem.setCartItemId(CommonUtils.uuid());
            //调用dao添加购物条目
            cartitemDao.add(cartitem);
        }
    }

    /**
     * 批量删除
     * @param cartItemIds
     */
    @Override
    public void batchDelete(String cartItemIds) {
        //把字符串转为数组
        String[] str = cartItemIds.split(",");
        //把要删除的购物条目的cartItemId出入集合中
        List<String> list = new ArrayList<>();
        for (int i = 0; i <str.length ; i++) {
            list.add(str[i]);
        }
        cartitemDao.batchDelete(list);
    }

    @Override
    public Cartitem upateQuantity(String cartItemId, String quantity) {
        //修改指定条目的数量
        cartitemDao.updateQuantity(Integer.parseInt(quantity),cartItemId);
        //根据cartItemId查询购物条目
        Cartitem cartitem = cartitemDao.findByCartItemId(cartItemId);
        return cartitem;
    }

    /**
     * 点击结算之后查看所勾选的条目
     * @param cartItemIds
     * @return
     */
    @Override
    public List<Cartitem> showCartItems(String cartItemIds) {
        //把字符串转为数组
        String[] str = cartItemIds.split(",");
        //把要删除的购物条目的cartItemId出入集合中
        List<String> list = new ArrayList<>();
        for (int i = 0; i <str.length ; i++) {
            list.add(str[i]);
        }
        System.out.println(list.size());
        List<Cartitem> cartitemList = cartitemDao.showByCartItemIds(list);
        return cartitemList;
    }
}
