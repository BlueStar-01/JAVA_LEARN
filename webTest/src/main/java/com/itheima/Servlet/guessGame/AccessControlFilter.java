package com.itheima.Servlet.guessGame;

import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.itheima.Servlet.guessGame.GuessNumberServlet.INPUT_URL;
import static com.itheima.Servlet.guessGame.GuessNumberServlet.NO_MESSAGE;


@WebFilter("/game_over.jsp") // 应用于
public class AccessControlFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false); // 不创建新会话

        String msg = NO_MESSAGE;
        if (session != null) {
            Object game = session.getAttribute("GuessGame");
            if (game == null || !((GuessGame) game).isOver()) {
                msg = "不可以作弊哦";
                httpResponse.sendRedirect(httpRequest.getContextPath() + INPUT_URL + "?message=" + URLEncoder.encode(msg, "UTF-8"));
                return;
            }
        } else {
            msg = "不可以作弊哦";
            httpResponse.sendRedirect(httpRequest.getContextPath() + INPUT_URL + "?message=" + URLEncoder.encode(msg, "UTF-8"));
            return;
        }
        session.invalidate();
        chain.doFilter(request, response);
    }
}