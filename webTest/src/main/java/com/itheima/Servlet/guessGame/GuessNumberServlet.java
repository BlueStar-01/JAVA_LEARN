package com.itheima.Servlet.guessGame;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;

@WebServlet("/GuessNumberServlet")
public class GuessNumberServlet extends HttpServlet {
    public static final String INPUT_URL = "/input.jsp";
    public static final String INVALID_GUESS_MESSAGE = "猜测的数字必须在0到100之间。";
    public static final String GAME_OVER_URL = "/game_over.jsp";
    public static final String NO_MESSAGE = ""; // 或者您可以设置一个默认的空消息


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String guessStr = req.getParameter("guess");
        String msg = NO_MESSAGE;

        if (guessStr != null) {
            try {
                Integer guess = Integer.parseInt(guessStr);
                if (guess > 100 || guess < 0) {
                    msg = INVALID_GUESS_MESSAGE;
                } else {
                    //获得会话
                    HttpSession session = req.getSession();
                    //获得猜谜对象（没有就创建）
                    GuessGame game = (GuessGame) session.getAttribute("GuessGame");
                    if (game == null) {
                        game = new GuessGame();
                        session.setAttribute("GuessGame", game);
                    }
                    msg = game.guess(guess);
                    if (game.check(guess)) {
                        resp.sendRedirect(req.getContextPath() + GAME_OVER_URL +
                                "?message=" + URLEncoder.encode(msg, "UTF-8") +
                                "&replayLink=" + URLEncoder.encode(req.getContextPath() + INPUT_URL, "UTF-8"));
                        return;
                    }
                }
            } catch (NumberFormatException e) {
                msg = "请输入一个有效的数字。";
            }
        }
        // 使用URL编码后的消息进行重定向
        resp.sendRedirect(req.getContextPath() + INPUT_URL + "?message=" + URLEncoder.encode(msg, "UTF-8"));
    }
}