package com.chen.service.impl;

import com.chen.dao.BookDao;
import com.chen.domain.Book;
import com.chen.service.BookService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 图书的service业务层接口实现类
 */
@Service
@Transactional
public class BookServiceImpl implements BookService {
    @Autowired
    private BookDao bookDao;

    /**
     * 按分类查图书
     * @param page
     * @param pageSize
     * @param cid
     * @return
     */
    @Override
    public List<Book> findByCategory(int page, int pageSize, String cid) {
        PageHelper.startPage(page,pageSize);
        return bookDao.findByCategory(cid);
    }

    /**
     * 按书名模糊查询
     * @param page
     * @param pageSize
     * @param bname
     * @return
     */
    @Override
    public List<Book> findByBname(int page, int pageSize, String bname) {
        PageHelper.startPage(page,pageSize);
        return bookDao.findByBname(bname);
    }

    /**
     * 按作者模糊查询
     * @param page
     * @param pageSize
     * @param author
     * @return
     */
    @Override
    public List<Book> findByAuthor(int page, int pageSize, String author) {
        PageHelper.startPage(page,pageSize);
        return bookDao.findByAuthor(author);
    }

    /**
     * 按出版社模糊查询
     * @param page
     * @param pageSize
     * @param press
     * @return
     */
    @Override
    public List<Book> findByPress(int page, int pageSize, String press) {
        PageHelper.startPage(page,pageSize);
        return bookDao.findByPress(press);
    }

    /**
     * 按书名，作者，出版社组合条件查询
     * @param page
     * @param pageSize
     * @param book
     * @return
     */
    @Override
    public List<Book> findCombination(int page, int pageSize, Book book) {
        PageHelper.startPage(page,pageSize);
        return bookDao.findCombination(book);
    }

    /**
     * 根据bid显示图书的详细信息
     * @param bid
     * @return
     */
    @Override
    public Book load(String bid) {
        return bookDao.findByBid(bid);
    }

    /**
     * 根据分类的cid去查询该分类下图书的个数
     * @param cid
     * @return
     */
    @Override
    public int findBookCountByCategory(String cid) {
        return bookDao.findBookCountByCategory(cid);
    }

    /**
     * 添加图书
     * @param book
     */
    @Override
    public void addBook(Book book) {
        bookDao.addBook(book);
    }

    /**
     * 修改图书
     * @param book
     */
    @Override
    public void updateBook(Book book) {
        bookDao.updateBook(book);
    }

    /**
     * 删除图书
     * @param book
     */
    @Override
    public void deleteBook(Book book) {
        bookDao.deleteBook(book);
    }
}
