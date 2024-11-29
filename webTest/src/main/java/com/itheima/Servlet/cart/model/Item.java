package com.itheima.Servlet.cart.model;

import lombok.Data;

@Data
public class Item {
    private Long id;
    private String name;
    private String description;
    private Integer price;
    private String image;
}
