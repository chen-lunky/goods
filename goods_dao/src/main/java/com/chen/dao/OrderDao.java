package com.chen.dao;

import com.chen.domain.Order;
import com.chen.domain.OrderItem;
import com.chen.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 订单模块的dao持久层接口
 */
public interface OrderDao {
    /**
     * 根据用户uid查询订单
     * @param uid
     * @return
     */
    @Select("SELECT * FROM `order` WHERE uid =  #{uid} ORDER BY ordertime desc ")
    @Results({
            @Result(id=true,property = "oid",column = "oid"),
            @Result(property = "ordertime",column = "ordertime"),
            @Result(property = "total",column = "total"),
            @Result(property = "status",column = "status"),
            @Result(property = "address",column = "address"),
            @Result(property = "user",column = "uid",javaType = User.class,one=@One(select = "com.chen.dao.UserDao.findByUid")),
            @Result(property = "orderItemList",column = "oid",javaType = List.class,one=@One(select = "com.chen.dao.OrderItemDao.findByOid")),
    })
    List<Order> findOrderByUser(@Param("uid") String uid);

    /**
     * 添加订单
     * @param order
     */
    @Insert("insert into `order` values(#{oid},#{ordertime},#{total},#{status},#{address},#{user.uid})")
    void addOrder(Order order);

    /**
     * 根据oid查询订单
     * @param oid
     * @return
     */
    @Select("select * from `order` where oid = #{oid}")
    @Results({
            @Result(id=true,property = "oid",column = "oid"),
            @Result(property = "ordertime",column = "ordertime"),
            @Result(property = "total",column = "total"),
            @Result(property = "status",column = "status"),
            @Result(property = "address",column = "address"),
            @Result(property = "user",column = "uid",javaType = User.class,one=@One(select = "com.chen.dao.UserDao.findByUid")),
            @Result(property = "orderItemList",column = "oid",javaType = List.class,one=@One(select = "com.chen.dao.OrderItemDao.findByOid")),
    })
    Order findByOid(@Param("oid") String oid);

    /**
     * 根据修改指定订单的状态
     * @param oid
     * @param status
     */
    @Update("update `order` set status = #{status} where oid = #{oid}")
    void updateStatus(@Param("oid") String oid, @Param("status") int status);

    /**
     * 查询所有订单
     * @return
     */
    @Select("select * from `order` order by ordertime desc")
    @Results({
            @Result(id=true,property = "oid",column = "oid"),
            @Result(property = "ordertime",column = "ordertime"),
            @Result(property = "total",column = "total"),
            @Result(property = "status",column = "status"),
            @Result(property = "address",column = "address"),
            @Result(property = "user",column = "uid",javaType = User.class,one=@One(select = "com.chen.dao.UserDao.findByUid")),
            @Result(property = "orderItemList",column = "oid",javaType = List.class,one=@One(select = "com.chen.dao.OrderItemDao.findByOid")),
    })
    List<Order> findAllOrder();

    /**
     * 根据status查询订单
     * @param status
     * @return
     */
    @Select("select * from `order` where status = #{status} order by ordertime desc")
    @Results({
            @Result(id=true,property = "oid",column = "oid"),
            @Result(property = "ordertime",column = "ordertime"),
            @Result(property = "total",column = "total"),
            @Result(property = "status",column = "status"),
            @Result(property = "address",column = "address"),
            @Result(property = "user",column = "uid",javaType = User.class,one=@One(select = "com.chen.dao.UserDao.findByUid")),
            @Result(property = "orderItemList",column = "oid",javaType = List.class,one=@One(select = "com.chen.dao.OrderItemDao.findByOid")),
    })
    List<Order> findByStatus(@Param("status") int status);

    /**
     * 按下单时间模糊查询
     * @param ordertime
     * @return
     */
    @Select("select * from `order` where ordertime like concat('%',#{ordertime},'%') order by ordertime desc")
    @Results({
            @Result(id=true,property = "oid",column = "oid"),
            @Result(property = "ordertime",column = "ordertime"),
            @Result(property = "total",column = "total"),
            @Result(property = "status",column = "status"),
            @Result(property = "address",column = "address"),
            @Result(property = "user",column = "uid",javaType = User.class,one=@One(select = "com.chen.dao.UserDao.findByUid")),
            @Result(property = "orderItemList",column = "oid",javaType = List.class,one=@One(select = "com.chen.dao.OrderItemDao.findByOid")),
    })
    List<Order> findByOrdertime(String ordertime);
}
