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
        if (session.isNew()) {
            session.setAttribute(AppConfigConstants.CART_KEY, new Cart(System.currentTimeMillis()));
//            // 创建cookie并设置会话ID
//            Cookie sessionIdCookie = new Cookie(AppConfigConstants.SESSION_ID_KEY, session.getId());
//            // 设置cookie的路径（可选，默认为当前路径）
//            sessionIdCookie.setPath("/");
//            // 如果需要，可以设置cookie的过期时间（以秒为单位）
//            sessionIdCookie.setMaxAge(60 * 60 * 24 * 7); // 例如，设置为一天后过期
//            // 设置HttpOnly标志（可选，但建议设置）
//            sessionIdCookie.setHttpOnly(true);
//            // 将cookie添加到响应中
//            resp.addCookie(sessionIdCookie);
        }
        //放行
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
