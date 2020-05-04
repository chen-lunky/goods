package com.chen.dao;

import com.chen.domain.Book;
import com.chen.domain.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 图书的dao持久层
 */
public interface BookDao {
    /**
     * 按分类查询图书
     * @param cid
     * @return
     */
    @Select("select * from book where cid = #{cid} ORDER BY orderBy ")
    List<Book> findByCategory(@Param("cid") String cid);

    /**
     * 按书名模糊查询
     * @param bname
     * @return
     */
    @Select("select * from book where bname like CONCAT('%',#{bname},'%') ORDER BY orderBy ")
    List<Book> findByBname(@Param("bname") String bname);

    /**
     * findByAuthor
     * @param author
     * @return
     */
    @Select("select * from book where author like CONCAT('%',#{author},'%') ORDER BY orderBy ")
    List<Book> findByAuthor(@Param("author") String author);

    /**
     * 按出版社模糊查询
     * @param press
     * @return
     */
    @Select("select * from book where press like CONCAT('%',#{press},'%') ORDER BY orderBy ")
    List<Book> findByPress(@Param("press") String press);

    /**
     * 按书名，作者，出版社组合条件查询
     * @param book
     * @return
     */
    @Select({"<script> select * from book where 1=1  " +
            "<if  test= \"bname != null and bname != ''\">and  bname like CONCAT('%',#{bname},'%') </if>" +
            "<if  test= \"author != null and author != ''\">   and author like CONCAT('%',#{author},'%') </if>" +
            "<if  test= \"press != null and press != ''\">  and press like CONCAT('%',#{press},'%') </if>" +
            "order by orderBy </script>"})
    List<Book> findCombination(Book book);

    /**
     * 根据bid显示图书的详细信息
     * @param bid
     * @return
     */
    @Select("select * from book where bid = #{bid}")
    @Results({
            @Result(id=true,property = "bid",column = "bid"),
            @Result(property = "bname",column = "bname"),
            @Result(property = "author",column = "author"),
            @Result(property = "price",column = "price"),
            @Result(property = "currPrice",column = "currPrice"),
            @Result(property = "discount",column = "discount"),
            @Result(property = "press",column = "press"),
            @Result(property = "publishtime",column = "publishtime"),
            @Result(property = "edition",column = "edition"),
            @Result(property = "pageNum",column = "pageNum"),
            @Result(property = "wordNum",column = "wordNum"),
            @Result(property = "printtime",column = "printtime"),
            @Result(property = "booksize",column = "booksize"),
            @Result(property = "paper",column = "paper"),
            @Result(property = "image_w",column = "image_w"),
            @Result(property = "image_b",column = "image_b"),
            @Result(property = "category",column = "cid",javaType = Category.class,one = @One(select = "com.chen.dao.CategoryDao.findByCid"))
    })
    Book findByBid(@Param("bid") String bid);

    /**
     * 根据分类的cid去查询该分类下图书的个数
     * @param cid
     * @return
     */
    @Select("select count(*) from book where cid = #{cid}")
    int findBookCountByCategory(@Param("cid") String cid);

    /**
     * 添加图书
     * @param book
     */
    @Insert("INSERT  INTO `book`(`bid`,`bname`,`author`,`price`,`currPrice`,`discount`,`press`,`publishtime`,`edition`,`pageNum`,`wordNum`,`printtime`,`booksize`,`paper`,`cid`,`image_w`,`image_b`) " +
            "VALUES(#{bid},#{bname},#{author},#{price},#{currPrice},#{discount},#{press},#{publishtime},#{edition},#{pageNum},#{wordNum},#{printtime},#{booksize},#{paper},#{category.cid},#{image_w},#{image_b})")
    void addBook(Book book);

    /**
     * 修改图书
     * @param book
     */
    @Update("update book set `bname`=#{bname},`author`=#{author},`price`=#{price},`currPrice`=#{currPrice}," +
            "`discount`=#{discount},`press`=#{press},`publishtime`=#{publishtime},`edition`=#{edition},`pageNum`=#{pageNum}," +
            "`wordNum`=#{wordNum},`printtime`=#{printtime},`booksize`=#{booksize},`paper`=#{paper},`cid`=#{category.cid} " +
            "where bid = #{bid}")
    void updateBook(Book book);

    /**
     * 根据指定的bid删除图书
     * @param book
     */
    @Delete("delete from book where bid = #{bid}")
    void deleteBook(Book book);
}
