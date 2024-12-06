package com.itheima.Servlet.cart.controller;

import com.itheima.Servlet.cart.common.AppConfigConstants;
import com.itheima.Servlet.cart.model.Cart;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/removeFromCart")
public class RemoveFromCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookIdStr = request.getParameter("id");
        Long bookId;
        try {
            bookId = Long.parseLong(bookIdStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "无效的ID格式");
            return;
        }
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(AppConfigConstants.CART_KEY);
        if (cart != null) {
            cart.removeItem(bookId);
            session.setAttribute(AppConfigConstants.CART_KEY, cart);
            response.sendRedirect(request.getContextPath() + AppConfigConstants.CART_JSP_URL); // 假设您的购物车页面是cart.jsp
        } else {
            response.sendRedirect(request.getContextPath() + AppConfigConstants.LOGIN_JSP_URL); // 假设您有一个登录页面
        }
    }
}