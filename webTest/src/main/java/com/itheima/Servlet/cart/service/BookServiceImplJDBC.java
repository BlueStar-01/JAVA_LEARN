package com.itheima.Servlet.cart.service;


import com.itheima.Servlet.cart.model.Book;
import com.itheima.Servlet.cart.model.Page;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BookServiceImplJDBC implements BookService {

    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/dingding";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "123456";


    @Override
    public Page<Book> page(int pageNo, int pageSize) {
        List<Book> books = new ArrayList<>();
        int total = 0;

        String sqlCount = "SELECT COUNT(*) FROM book";
        String sqlQuery = "SELECT * FROM book LIMIT ?, ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement pstCount = conn.prepareStatement(sqlCount);
             ResultSet rsCount = pstCount.executeQuery()) {

            if (rsCount.next()) {
                total = rsCount.getInt(1);
            }

            try (PreparedStatement pstQuery = conn.prepareStatement(sqlQuery)) {
                int offset = (pageNo - 1) * pageSize;
                pstQuery.setInt(1, offset);
                pstQuery.setInt(2, pageSize);

                try (ResultSet rs = pstQuery.executeQuery()) {
                    while (rs.next()) {
                        Book book = new Book();
                        book.setId(rs.getLong("id"));
                        book.setName(rs.getString("name"));
                        book.setAuthor(rs.getString("author"));
                        book.setCoverImg(rs.getString("cover_img"));
                        book.setIsbn(rs.getString("ISBN"));
                        book.setPrice(rs.getInt("price"));
                        books.add(book);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Page<Book> page = new Page<>(pageNo, pageSize);
        page.setTotal(total);
        page.setRecords(books);
        return page;
    }
}