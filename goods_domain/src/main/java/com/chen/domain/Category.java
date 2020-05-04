package com.chen.domain;


import java.io.Serializable;
import java.util.List;

public class Category implements Serializable {
    private String cid;//主键
    private String cname;//图书分类名称
    private String desc;//描述
    private Category parent;//父分类
    private List<Category> children;//子分类

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public List<Category> getChildren() {
        return children;
    }

    public void setChildren(List<Category> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "Category{" +
                "cid='" + cid + '\'' +
                ", cname='" + cname + '\'' +
                ", desc='" + desc + '\'' +
                ", parent=" + parent +
                ", children=" + children +
                '}';
    }
}
