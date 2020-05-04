package com.chen.service;

import com.chen.domain.Category;

import java.util.List;

/**
 * 图书分类模块的service业务层接口
 */
public interface CategoryService {
    //查询所有
    List<Category> findAll();
    //添加一级分类
    void addParent(Category category);
    //查询所有父分类
    List<Category> findParents();
    //根据cid查询子分类的个数
    int findChildCountByCid(String cid);
    //删除分类
    void deleteCategory(String cid);
    //根据cid查询分类
    Category findCategoryByCid(String cid);
    //修改分类
    void updateCategory(Category category);
    //根据父分类的cid查询所有的子分类
    List<Category> findChildCategroyByPid(String pid);
}
