package com.itheima.Servlet.cart.filter;

import com.itheima.Servlet.cart.common.AppConfigConstants;
import com.itheima.Servlet.cart.model.Cart;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/cart")
public class CartFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        HttpSession session = req.getSession();
        //创建会话
        session.setAttribute(AppConfigConstants.CART_KEY, new Cart(System.currentTimeMillis()));
        //放行
        filterChain.doFilter(servletRequest, servletResponse);

    }
}
