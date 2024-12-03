package com.itheima.Servlet.cart.model;

import lombok.Data;

import java.util.List;

@Data
public class Page<T> {
    private int page;
    private int size;
    private int total;
    private List<T> records;

    public Page(int i, int i1) {
        page = i;
        size = i1;
    }
}
