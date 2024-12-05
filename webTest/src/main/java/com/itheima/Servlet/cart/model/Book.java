package com.itheima.Servlet.cart.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import java.util.Date;

/**
 * /**
 * 书籍信息表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book extends Item implements Serializable {
    private Long id;
    private String name;
    private String author;
    private String isbn;
    private Integer price;
    private String coverImg;
}
