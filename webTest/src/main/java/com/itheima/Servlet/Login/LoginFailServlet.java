package com.itheima.Servlet.Login;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/LoginFailServlet")
public class LoginFailServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置错误信息（这通常是从之前的登录尝试中传递过来的）
        String errorMessage = "用户名或者密码错误";
        req.setAttribute("errorMessage", errorMessage);

        // 使用请求转发器将请求转发到 Login.jsp
        RequestDispatcher dispatcher = req.getRequestDispatcher("/login.jsp");
        dispatcher.forward(req, resp);
    }
}
