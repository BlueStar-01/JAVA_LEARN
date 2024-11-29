package com.itheima.Servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/LoginSuccessServlet")
public class LoginSuccessServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Object msg = req.getAttribute("msg");
        res.setContentType("text/html;charset=UTF-8");
        PrintWriter out = res.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet LoginSucessServlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>" + msg + "</h1>");
        out.println("</body>");

    }
}
