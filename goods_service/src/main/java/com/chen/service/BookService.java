package com.chen.service;

import com.chen.domain.Book;

import java.util.List;

/**
 * 图书的service业务层接口
 */
public interface BookService {

    //按分类查询图书
    List<Book> findByCategory(int page, int pageSize, String cid);

    //按书名模糊查询
    List<Book> findByBname(int page, int pageSize, String bname);
   //按作者模糊查询
    List<Book> findByAuthor(int page, int pageSize, String author);
   //按出版社模糊查询
    List<Book> findByPress(int page, int pageSize, String press);
    //按书名，作者，出版社组合条件查询
    List<Book> findCombination(int page, int pageSize, Book book);
   //根据bid显示图书的详细信息
    Book load(String bid);
   //根据分类的cid去查询该分类下图书的个数
    int findBookCountByCategory(String cid);
    //添加图书
    void addBook(Book book);
   //修改图书
    void updateBook(Book book);
    //删除图书
    void deleteBook(Book book);
}
