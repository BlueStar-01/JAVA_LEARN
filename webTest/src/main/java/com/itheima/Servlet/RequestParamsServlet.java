package com.itheima.Servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/RequestParamsServlet")
public class RequestParamsServlet extends HttpServlet {

    private static final String OperatorServletURL = "/OperatorServlet";


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置响应的内容类型为HTML，并指定字符编码为UTF-8
        resp.setContentType("text/html; charset=UTF-8");
        // 获取PrintWriter对象用于输出响应内容
        PrintWriter out = resp.getWriter();

        Integer num1 = null;
        Integer num2 = null;
        try {
            num1 = Integer.parseInt(req.getParameter("num1"));
            num2 = Integer.parseInt(req.getParameter("num2"));
        } catch (NumberFormatException e) {
            out.println("<html>");
            out.println("<head>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>" + "数字转换错误" + "</h1>");
            out.println("</body>");
            out.println("</html>");
            throw new RuntimeException(e);
        }
        String operator = req.getParameter("operator");
        if (operator != null && (operator.equals("+") || operator.equals("-") || operator.equals("*") || operator.equals("/"))) {
            req.setAttribute("num1", num1);
            req.setAttribute("num2", num2);
            RequestDispatcher dispatcher = req.getRequestDispatcher(OperatorServletURL);
            dispatcher.forward(req, resp);
        } else {
            try {
                out.println("<html>");
                out.println("<head>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>" + "运算符错误" + ": " + operator + "应为+ - * /" + "</h1>");
                out.println("</body>");
                out.println("</html>");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                out.close();
            }
        }
    }

}
