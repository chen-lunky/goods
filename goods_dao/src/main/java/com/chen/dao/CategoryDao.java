package com.chen.dao;

import com.chen.domain.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 图书分类模块的dao持久层接口
 */
public interface CategoryDao {

    /**
     * 查询所有父分类
     * @return
     */

    @Select("select * from category where pid is null order by orderBy")
    List<Category> findAllParent();

    /**
     * 根据父分类的cid查询它的子分类
     * @return
     */
    @Select("select * from category where pid = #{pid}")
    List<Category> findByPid(@Param("pid")String pid);

    /**
     * 根据cid查询分类
     * @param cid
     * @return
     */
    @Select("select * from category where cid = #{cid}")
    @Results({
            @Result(id=true,property = "cid",column = "cid"),
            @Result(property = "cname",column = "cname"),
            @Result(property = "desc",column = "desc"),
            @Result(property = "parent",column = "pid",javaType = Category.class,one=@One(select = "com.chen.dao.CategoryDao.findByCid")),
            @Result(property = "children",column = "cid",javaType = List.class,many=@Many(select = "com.chen.dao.CategoryDao.findByPid"))
    })
    Category findByCid(@Param("cid")String cid);

    /**
     * 添加一级分类或二级分类
     * @param category
     */
    @Insert("insert into category(cid,cname,pid,`desc`) values(#{cid},#{cname},#{parent.cid},#{desc})")
    void add(Category category);

    /**
     * 根据父分类cid查询子分类的个数
     * @param cid
     * @return
     */
    @Select("select count(*) from category where pid = #{pid}")
    int findChildCountByCid(@Param("pid") String cid);

    /**
     * 删除分类
     * @param cid
     */
    @Delete("delete from category where cid = #{cid}")
    void deleteCategory(@Param("cid") String cid);

    /**
     * 修改分类
     * @param category
     */
    @Update("update category set cname=#{cname},pid=#{parent.cid},`desc`=#{desc} where cid = #{cid}")
    void updateCategory(Category category);
}
