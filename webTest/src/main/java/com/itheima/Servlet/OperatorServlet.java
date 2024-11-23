package com.itheima.Servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/OperatorServlet")
public class OperatorServlet extends HttpServlet {

    private static final String RESULT_SERVLET_URL = "/ResultServlet";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String num1 = request.getParameter("num1");
        String num2 = request.getParameter("num2");
        String operator = request.getParameter("operator");

        String result;
        if (operator.equals("+")) {
            result = String.valueOf(Integer.parseInt(num1) + Integer.parseInt(num2));
        } else if (operator.equals("-")) {
            result = String.valueOf(Integer.parseInt(num1) - Integer.parseInt(num2));
        } else if (operator.equals("*")) {
            result = String.valueOf(Integer.parseInt(num1) * Integer.parseInt(num2));
        } else if (operator.equals("/")) {
           try {
                result = String.valueOf(Integer.parseInt(num1) / Integer.parseInt(num2));
            }catch (Exception e) {
               result="请不要把作为除数￣へ￣";
               e.printStackTrace();

           }
        } else {
            result = "运算符号不支持";
        }
        System.out.println("result=" + result);
        request.setAttribute("result", result);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(RESULT_SERVLET_URL);
        requestDispatcher.forward(request, response);
    }


}
