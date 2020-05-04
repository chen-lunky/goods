package com.chen.service.impl;

import com.chen.dao.CategoryDao;
import com.chen.domain.Category;
import com.chen.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 图书分类模块的service业务层接口实现类
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDao categoryDao;

    /**
     * 查询所有分类
     * @return
     */
    @Override
    public List<Category> findAll() {
        //获取所有父分类
        List<Category> parents = categoryDao.findAllParent();
        for (Category parent : parents) {
            //获取每一个父分类的子分类
            List<Category> children = categoryDao.findByPid(parent.getCid());
            parent.setChildren(children);
        }

        return parents;
    }

    /**
     * 添加一级分类
     * @param category
     */
    @Override
    public void addParent(Category category) {
        categoryDao.add(category);
    }

    /**
     * 查询所有父分类
     * @return
     */
    @Override
    public List<Category> findParents() {
        return categoryDao.findAllParent();
    }

    /**
     *  根据cid查询子分类的个数
     * @param cid
     * @return
     */
    @Override
    public int findChildCountByCid(String cid) {
        return categoryDao.findChildCountByCid(cid);
    }

    /**
     *  删除分类
     * @param cid
     */
    @Override
    public void deleteCategory(String cid) {
         categoryDao.deleteCategory(cid);
    }

    /**
     * 根据cid查询分类
     * @param cid
     * @return
     */
    @Override
    public Category findCategoryByCid(String cid) {
        return categoryDao.findByCid(cid);
    }

    /**
     * 修改分类
     * @param category
     */
    @Override
    public void updateCategory(Category category) {
        categoryDao.updateCategory(category);
    }

    /**
     * 根据父分类的cid查询所有的子分类
     * @param pid
     * @return
     */
    @Override
    public List<Category> findChildCategroyByPid(String pid) {
        return categoryDao.findByPid(pid);
    }
}
