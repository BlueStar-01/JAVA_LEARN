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

@WebServlet("/cart")
public class cartServlet extends HttpServlet {

    @Override
    /**
     * 添加商品
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        //取出商品，和添加的参数。
        Cart cart = (Cart) session.getAttribute(AppConfigConstants.CART_KEY);
        String itemId = req.getParameter(AppConfigConstants.ITEM_ID_KEY);
        String addNumber = req.getParameter(AppConfigConstants.ITEM_ADD_NUMBER_KEY);

        if (cart == null) {

            return;
        }
        //添加商品，返回成功页面;
        Cart  row = cart.getCartMap().get(Integer.parseInt(itemId));


    }


}
