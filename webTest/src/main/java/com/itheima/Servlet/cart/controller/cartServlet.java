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
import java.util.List;

@WebServlet("/cart")
public class cartServlet extends HttpServlet {

    @Override
    /**
     * 添加商品
     */
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        //取出商品，和添加的参数。
        Cart cart = (Cart) session.getAttribute(AppConfigConstants.CART_KEY);
        String itemId = req.getParameter(AppConfigConstants.ITEM_ID_KEY);
        String addNumber = req.getParameter(AppConfigConstants.ITEM_ADD_NUMBER_KEY);

        if (cart == null) {
            session.setAttribute(AppConfigConstants.ERROR_MESSAGE_KEY, "空购物车分路(•́へ•́╬)");
            resp.sendRedirect(req.getContextPath() + AppConfigConstants.ERROR_JSP_URL);
            return;
        }
        //添加商品，返回成功页面;
        cart.addItem(Integer.parseInt(itemId), Integer.parseInt(addNumber));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute(AppConfigConstants.CART_KEY);

        if (cart == null) {
            session.setAttribute(AppConfigConstants.ERROR_MESSAGE_KEY, "空购物车分路(•́へ•́╬)");
            resp.sendRedirect(req.getContextPath() + AppConfigConstants.ERROR_JSP_URL);
            return;
        }

        // 获取分页参数
        int currentPage = 1;
        int pageSize = 10; // 每页显示的商品数量
        String pageParam = req.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            currentPage = Integer.parseInt(pageParam);
        }

        // 计算分页数据
        List allItems = cart.getAllItems();
        int totalPages = (int) Math.ceil((double) allItems.size() / pageSize);
        int fromIndex = (currentPage - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, allItems.size());
        List pagedItems = allItems.subList(fromIndex, toIndex);

        // 将分页数据和总页数设置到请求属性中
        req.setAttribute("pagedItems", pagedItems);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("totalPages", totalPages);

        // 转发到JSP页面显示购物车内容
        req.getRequestDispatcher(AppConfigConstants.CART_JSP_URL).forward(req, resp);
    }
}
