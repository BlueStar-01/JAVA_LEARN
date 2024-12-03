package com.itheima.Servlet.cart.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "CrossOriginFilter", urlPatterns = "/*")
public class CrossOriginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化逻辑（可选）
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 设置响应参数格式
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 允许跨域的主机地址（可以设置为具体域名或*）
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");

        // 允许跨域的请求方法（可以设置为具体方法或用*表示所有方法）
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, HEAD, OPTIONS, PUT, DELETE");

        // 重新预检验跨域的缓存时间（秒）
        httpResponse.setHeader("Access-Control-Max-Age", "3600");

        // 允许跨域的请求头（可以设置为具体头或用*表示所有头）
        httpResponse.setHeader("Access-Control-Allow-Headers", "*");

        // 是否携带cookie（如果设置为true，则Access-Control-Allow-Origin不能设置为*）
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");

        // 继续执行过滤器链中的下一个过滤器或目标资源
        chain.doFilter(request, httpResponse);
    }

    @Override
    public void destroy() {
        // 销毁逻辑（可选）
    }
}