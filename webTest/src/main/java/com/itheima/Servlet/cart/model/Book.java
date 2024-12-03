package com.itheima.Servlet.cart.model;


import lombok.Data;

import java.io.Serializable;

import java.util.Date;

/**
 * /**
 * 书籍信息表
 */
@Data
public class Book implements Serializable {

    /**
     * 书籍的唯一标识符
     */
    private Long id;
    /**
     * 书籍的名称
     */
    private String name;
    /**
     * 书籍的封面图片URL或路径
     */
    private String coverImg;
    /**
     * 书籍的详细描述
     */
    private String description;
    /**
     * 书籍的国际标准书号
     */
    private String isbn;
    /**
     * 书籍的作者
     */
    private String author;
    /**
     * 出版社的名称
     */
    private String publishing;
    /**
     * 书籍的出版日期
     */
    private Date publishingDate;
    /**
     * 记录创建时间
     */
    private Date createTime;
    /**
     * 记录最后更新时间
     */
    private Date updateTime;
    /**
     * 价格
     */
    private Integer price;
}
