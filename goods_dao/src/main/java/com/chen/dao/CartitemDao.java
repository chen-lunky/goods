package com.chen.dao;

import com.chen.domain.Book;
import com.chen.domain.Cartitem;
import com.chen.domain.User;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 * 购物车模块的dao持久层接口
 */
public interface CartitemDao {
    /**
     * 根据当前用户的uid去查询他购物车的购物信息
     * @param uid
     * @return
     */
    @Select("select * from cartitem where uid = #{uid} order by orderBy")
    @Results({
            @Result(id=true,property = "cartItemId",column = "cartItemId"),
            @Result(property = "quantity",column = "quantity"),
            @Result(property = "book",column = "bid",javaType = Book.class,one=@One(select = "com.chen.dao.BookDao.findByBid")),
            @Result(property = "user",column = "uid",javaType = User.class,one=@One(select = "com.chen.dao.UserDao.findByUid")),

    })
    List<Cartitem> findAllByUid(@Param("uid") String uid);

    /**
     * 根据用户uid和图书bid查询购物条目
     * @param uid
     * @param bid
     * @return
     */
    @Select("select * from cartitem where uid = #{uid} and bid = #{bid}")
    Cartitem findByUidAndBid(@Param("uid") String uid, @Param("bid") String bid);

    /**
     * 修改条目的数量
     * @param quantity
     * @param cartItemId
     */
    @Update("update  cartitem set quantity = #{quantity} where cartItemId = #{cartItemId}")
    void updateQuantity(@Param("quantity") int quantity, @Param("cartItemId") String cartItemId);

    /**
     * 添加购物条目
     * @param cartitem
     */
    @Insert("insert into cartitem(cartItemId,quantity,bid,uid) values(#{cartItemId},#{quantity},#{book.bid},#{user.uid})")
    void add(Cartitem cartitem);

    /**
     *批量删除
     * <script>DELETE from hscs_itf_defination_lines where HEADER_ID in
     *         <foreach collection="list" item="id"
     *                  open="(" close=")" index="index" separator=",">
     *             #{id}
     *         </foreach>
     *         </script>
     *
     * <script> delete from emp where empno in
     * 		<foreach collection="array" item="arr" index="no" open="("
     * 			separator="," close=")">
     * 			#{arr}
     * 		</foreach>
     * 	</script>
     *
     * 	foreach元素的属性主要有item，index，collection，open，separator，close。
     * 	   item表示集合中每一个元素进行迭代时的别名，
     * 	   index指定一个名字，用于表示在迭代过程中，每次迭代到的位置，
     * 	   open表示该语句以什么开始，
     * 	   separator表示在每次进行迭代之间以什么符号作为分隔符，
     * 	   close表示以什么结束，
     * 在使用foreach的时候最关键的也是最容易出错的就是collection属性，该属性是必须指定的，
     * 但是在不同情况下，该属性的值是不一样的，主要有一下3种情况：
     *      1.如果传入的是单参数且参数类型是一个List的时候，collection属性值为list .
     *      2.如果传入的是单参数且参数类型是一个array数组的时候，collection的属性值为array .
     *      3.如果传入的参数是多个的时候，我们就需要把它们封装成一个Map了，当然单参数也可以封装成map，
     *       实际上如果你在传入参数的时候，在MyBatis里面也是会把它封装成一个Map的，map的key就是参数名，
     *       所以这个时候collection属性值就是传入的List或array对象在自己封装的map里面的key.
     * @param
     */

    @Delete("<script>" +
            "delete from cartitem where cartItemId in" +
            "<foreach collection=\"list\" item=\"item\"" +
            " open=\"(\" close=\")\" index=\"index\" separator=\",\">" +
            "#{item}" +
            "</foreach>" +
            "</script>")
    void batchDelete(List<String> list);

    /**
     * 修改指定条目的数量
     * @param cartItemId
     * @return
     */
    @Select("select * from cartitem where cartItemId = #{cartItemId}")
    @Results({
            @Result(id=true,property = "cartItemId",column = "cartItemId"),
            @Result(property = "quantity",column = "quantity"),
            @Result(property = "book",column = "bid",javaType = Book.class,one=@One(select = "com.chen.dao.BookDao.findByBid")),
            @Result(property = "user",column = "uid",javaType = User.class,one=@One(select = "com.chen.dao.UserDao.findByUid")),

    })
    Cartitem findByCartItemId(@Param("cartItemId") String cartItemId);

    /**
     * 点击结算之后查看所勾选的条目
     * @param list
     * @return
     */
    @Select("<script>" +
            "select * from cartitem where cartItemId in" +
            "<foreach collection=\"list\" item=\"item\"" +
            " open=\"(\" close=\")\" index=\"index\" separator=\",\">" +
            "#{item}" +
            "</foreach>" +
            "</script>")
    @Results({
            @Result(id=true,property = "cartItemId",column = "cartItemId"),
            @Result(property = "quantity",column = "quantity"),
            @Result(property = "book",column = "bid",javaType = Book.class,one=@One(select = "com.chen.dao.BookDao.findByBid")),
            @Result(property = "user",column = "uid",javaType = User.class,one=@One(select = "com.chen.dao.UserDao.findByUid")),

    })
    List<Cartitem> showByCartItemIds(List<String> list);
}
