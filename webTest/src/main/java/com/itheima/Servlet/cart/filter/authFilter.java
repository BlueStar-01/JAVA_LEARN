package com.itheima.Servlet.cart.filter;

import com.itheima.Servlet.cart.common.AppConfigConstants;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "authFilter", urlPatterns = "/success.jsp")
public class authFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            HttpSession session = httpRequest.getSession(false); // 不创建新会话

            Object loginKey = session.getAttribute(AppConfigConstants.LOGGED_IN_SESSION_ATTR);
            if (session == null || loginKey == null
                    || !loginKey.equals(AppConfigConstants.LOGGED_SUCCESS_VALUE)) {
                httpResponse.sendRedirect(httpRequest.getContextPath() + AppConfigConstants.LOGIN_JSP_URL);
                return;
            }
        }
        chain.doFilter(request, response);
    }
}