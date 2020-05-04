package com.chen.dao;

import com.chen.domain.Book;
import com.chen.domain.OrderItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 订单条目模块的dao持久层接口
 */
public interface OrderItemDao {
    /**
     * 根据订单oid查询订单条目
     * @param oid
     * @return
     */
    @Select("select * from orderitem where oid = #{oid}")
    @Results({
           @Result(id = true,property = "orderItemId",column = "orderItemId"),
           @Result(property = "quantity",column = "quantity"),
           @Result(property = "subtotal",column = "subtotal"),
           @Result(property = "book",column = "bid",javaType = Book.class,one = @One(select = "com.chen.dao.BookDao.findByBid"))

    })
    List<OrderItem> findByOid(@Param("oid")String oid);

    /**
     * 根据OrderItemId查询订单条目
     * @param orderItemId
     * @return
     */
    @Select("select * from orderitem where orderItemId = #{orderItemId}")
        @Results({
           @Result(id = true,property = "orderItemId",column = "orderItemId"),
           @Result(property = "quantity",column = "quantity"),
           @Result(property = "subtotal",column = "subtotal"),
           @Result(property = "book",column = "bid",javaType = Book.class,one = @One(select = "com.chen.dao.BookDao.findByBid"))

    })
    OrderItem  findByOrderItemId(@Param("orderItemId")String orderItemId);

    /**
     * 批量添加订单条目
     *  insert into redeem_code
     *            (bach_id, code, type, facevalue,create_user,create_time)
     *            values
     *            <foreach collection ="list" item="reddemCode" index= "index" separator =",">
     *                (
     *                 #{reddemCode.batchId}, #{reddemCode.code},
     *                #{reddemCode.type},
     *               #{reddemCode.facevalue},
     *               #{reddemCode.createUser}, #{reddemCode.createTime}
     *               )
     *            </foreach >
     *            //            "#{item.orderItemId},#{item.quantity},#{item.subtotal},#{item.book.bid},#{item.book.bname},#{item.book.currPrice},#{item.book.image_b},#{item.order.oid}" +
     * @param orderItemList
     */
    @Insert("<script>" +
            "INSERT INTO `orderitem`(orderItemId,quantity,subtotal,bid,bname,currPrice,image_b,oid) values" +
            "<foreach collection=\"list\" item=\"item\"" +
            "  index=\"index\" separator=\",\">" +
            "(" +
            "#{item.orderItemId},#{item.quantity}," +
            "#{item.subtotal},#{item.book.bid}," +
            "#{item.book.bname},#{item.book.currPrice}," +
            "#{item.book.image_b},#{item.order.oid}" +
            ")" +
            " </foreach>" +
            "</script>")
    void addOrderItem(List<OrderItem> orderItemList);

}
