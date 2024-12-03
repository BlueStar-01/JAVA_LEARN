package com.itheima.Servlet.cart.service;

import com.itheima.Servlet.cart.model.Book;
import com.itheima.Servlet.cart.model.Page;

public interface BookService {
    Page<Book> page(int pageNo, int pageSize);
}