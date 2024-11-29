package com.itheima.Servlet.计算器实验;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/ResultServlet")
public class ResultServlet extends HttpServlet {
    private static final String KEY = "result";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 从请求属性中获取result值
        String result = (String) req.getAttribute("result");

        // 设置响应内容类型
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();

        // 生成HTML响应，包含结果
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\">");
        out.println("<title>计算结果</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>计算结果是：" + (result != null ? result : "未知") + "</h1>"); // 显示结果
        out.println("</body>");
        out.println("</html>");
    }
}
