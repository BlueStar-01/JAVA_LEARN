//package com.itheima.Servlet.cart.filter;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@WebFilter(filterName = "CrossOriginFilter", urlPatterns = "/*")
//public class CrossOriginFilter implements Filter {
//
//    @Override
//    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
//            throws IOException, ServletException {
//
//        HttpServletResponse response = (HttpServletResponse) res;
//        HttpServletRequest request = (HttpServletRequest) req;
//
//        // 设置CORS相关的HTTP头
//        response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173"); // 替换为你的允许源
//        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
//        response.setHeader("Access-Control-Allow-Headers", "Origin, Content-Type, Accept, Authorization");
//        response.setHeader("Access-Control-Allow-Credentials", "true"); // 允许携带Cookie
//
//        // 对于预检请求（OPTIONS），直接返回响应，不再继续处理
//        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
//            response.setStatus(HttpServletResponse.SC_OK);
//        } else {
//            // 否则，继续处理请求
//            chain.doFilter(req, res);
//        }
//    }
//
//}