package com.itheima.Servlet.圆半径实验;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

@WebServlet("/CircleServlet")
public class CircleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Circle circle = new Circle(3);
        resp.setContentType("text/plain");
        PrintWriter out = resp.getWriter();
        out.println(circle.getArea());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletInputStream inputStream = req.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String s = bufferedReader.readLine();
        System.out.println(s);
        Circle circle = new Circle(Double.parseDouble(s.split("=")[1]));
        resp.setContentType("text/plain");
        PrintWriter out = resp.getWriter();
        out.println(circle.getArea());
    }
}
