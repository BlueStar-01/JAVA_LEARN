<%@ page import="com.itheima.Servlet.cart.common.AppConfigConstants" %>
<%@ page import="com.itheima.Servlet.cart.model.Cart" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>结算成功</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .success-message {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
            width: 300px;
            padding: 20px;
            text-align: center;
            border-radius: 5px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
    </style>
</head>
<body>
<div class="success-message">
    <h2>结算成功！</h2>
    <p>您的订单已成功处理。</p>
    <%
        Cart cart = (Cart) session.getAttribute(AppConfigConstants.CART_KEY);
        cart.clean();
        session.setAttribute(AppConfigConstants.CART_KEY, cart);
    %>
    <a href="cart.jsp">返回购物车</a> | <a href="itemServlet">继续购物</a>
</div>
</body>
</html>