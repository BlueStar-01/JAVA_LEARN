package com.itheima.Servlet.cart.controller;

import com.itheima.Servlet.cart.common.AppConfigConstants;
import com.itheima.Servlet.cart.model.Book;
import com.itheima.Servlet.cart.model.Page;
import com.itheima.Servlet.cart.service.BookService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


import com.itheima.Servlet.cart.service.BookServiceImplJDBC;

@WebServlet("/itemServlet")
public class itemServlet extends HttpServlet {

    private BookService bookService;

    public itemServlet() {
        this.bookService = new BookServiceImplJDBC();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pageNo = req.getParameter("pageNo");
        Page<Book> pageRequest = null;
        try {
            pageRequest = new Page<>(Integer.parseInt(pageNo),5);
        } catch (NumberFormatException e) {
            pageRequest = new Page<>(1, 5);
        }

        Page<Book> bookPage = bookService.page(pageRequest.getPage(), pageRequest.getSize()); // 这里需要确保 page 方法接受这两个参数

        //servlet
        req.setAttribute("currentPage", bookPage.getPage());
        int totalPages = (int) Math.ceil((double) bookPage.getTotal() / bookPage.getSize());
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("books", bookPage.getRecords());
        req.setAttribute("bookPage", bookPage);
        req.getRequestDispatcher(AppConfigConstants.ITEM_JSP_URL).forward(req, resp);
    }
}