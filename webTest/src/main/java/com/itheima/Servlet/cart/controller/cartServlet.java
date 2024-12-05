package com.itheima.Servlet.cart.controller;

import cn.hutool.log.Log;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.Servlet.cart.common.AppConfigConstants;
import com.itheima.Servlet.cart.common.JsonUt;
import com.itheima.Servlet.cart.model.Book;
import com.itheima.Servlet.cart.model.Cart;
import com.itheima.Servlet.cart.model.Item;
import com.itheima.Servlet.cart.model.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/cartServlet")
public class cartServlet extends HttpServlet {
    public cartServlet() {
        super();
        objectMapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 从请求中获取表单参数
        String bookId = request.getParameter("id");
        String bookName = request.getParameter("name");
        String bookAuthor = request.getParameter("author");
        String bookIsbn = request.getParameter("isbn");
        String bookPriceStr = request.getParameter("price"); // 注意：这可能是字符串，需要转换为数值类型
        String bookCoverImg = request.getParameter("coverImg");

        try {
            // 将价格字符串转换为数值类型（假设是BigDecimal）
            BigDecimal bookPrice = new BigDecimal(bookPriceStr);
            // 创建Book对象（这里假设Book有一个构造函数或setter方法可以设置这些属性）
            Book cartItem = new Book(Long.parseLong(bookId), bookName, bookAuthor, bookIsbn, Integer.parseInt(bookPriceStr), bookCoverImg);
            // 处理业务逻辑
            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute(AppConfigConstants.CART_KEY);
            if (cart == null) {
                cart = new Cart(System.currentTimeMillis());
            }
            cart.addItem(cartItem, 1);
            session.setAttribute(AppConfigConstants.CART_KEY, cart);
            // 假设操作成功，返回响应
            //JsonUt.JSONtoResp(response, Result.success("添加成功"));
            request.getRequestDispatcher(AppConfigConstants.CART_JSP_URL).forward(request, response);
        } catch (NumberFormatException e) {
            // 处理价格转换异常
            JsonUt.JSONtoResp(response, Result.error("价格格式错误: " + e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUt.JSONtoResp(response, Result.error("服务器出错: " + e.getMessage()));
        }
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
        List allItems = cart.getAllRow();
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
