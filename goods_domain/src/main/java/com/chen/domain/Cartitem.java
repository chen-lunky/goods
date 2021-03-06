package com.chen.domain;

import java.math.BigDecimal;

/**
 * 购物车模块的实体类
 */
public class Cartitem {
    private String cartItemId;//主键
    private int quantity;//购买某本书的数量
    private Book book;
    private User user;

    //得到条目的小计
    public double getSubTotal(){
        BigDecimal b1 = new BigDecimal(book.getCurrPrice()+"");
        BigDecimal b2 = new BigDecimal(quantity+"");
        BigDecimal b3 = b1.multiply(b2);
        return b3.doubleValue();
    }
    public String getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(String cartItemId) {
        this.cartItemId = cartItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
