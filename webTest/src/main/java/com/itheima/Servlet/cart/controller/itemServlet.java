package com.itheima.Servlet.cart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.Servlet.cart.model.Book;
import com.itheima.Servlet.cart.model.Page;
import com.itheima.Servlet.cart.model.Result;
import com.itheima.Servlet.cart.service.BookService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


import com.itheima.Servlet.cart.service.BookServiceImplJDBC;

@WebServlet("/book/page")
public class itemServlet extends HttpServlet {

    private BookService bookService;

    public itemServlet() {
        this.bookService = new BookServiceImplJDBC();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pageNo = req.getParameter("pageNo");
        String size = req.getParameter("pageSize");

        // 注意：这里您应该使用 Page 对象的构造函数来设置当前页和每页大小
        // 但是由于您的 BookServiceImplJDBC 的 page 方法没有返回 Page 对象，这里需要调整
        // 假设您已经修改了 BookServiceImplJDBC 以返回 Page<Book>
        Page<Book> pageRequest = new Page<>(Integer.parseInt(pageNo), Integer.parseInt(size));
        Page<Book> bookPage = bookService.page(pageRequest.getPage(), pageRequest.getSize()); // 这里需要确保 page 方法接受这两个参数

        Result<Page<Book>> result = Result.success(bookPage);

        // 设置响应内容类型为JSON
        resp.setContentType("application/json;charset=UTF-8");

        // 使用 Jackson 将对象转换为 JSON 字符串
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(result);

        // 将 JSON 字符串写入响应体
        resp.getWriter().write(jsonString);

    }
}