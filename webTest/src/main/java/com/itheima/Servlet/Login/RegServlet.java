package com.itheima.Servlet.Login;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/RegServlet", initParams =
        {
                @WebInitParam(name = "username", value = "babyface"),
                @WebInitParam(name = "password", value = "123456")
        })
public class RegServlet extends HttpServlet {

    private static final String SUCCESS_URL = "/LoginSuccessServlet";
    private static final String FAIL_URL = "/LoginFailServlet";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("username");
        String password = req.getParameter("password");

        if (name != null && password != null) {
            if (name.equals(getInitParameter("username")) && password.equals(getInitParameter("password"))) {
                log("密码正确");
                req.setAttribute("msg", "欢迎你登录" + name + "(*╹▽╹*)");
                req.getRequestDispatcher(SUCCESS_URL).forward(req, resp);
                return;
            }
        }
        req.getRequestDispatcher(FAIL_URL).forward(req, resp);
    }
}
