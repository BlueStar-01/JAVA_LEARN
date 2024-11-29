<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>猜数字游戏结果</title>
</head>
<body>
<h1>猜数字游戏结果</h1>
<!-- 显示提示信息 -->
<p><%= request.getParameter("message") %></p>
<!-- 显示重新玩游戏的链接 -->
<a href="<%= request.getParameter("replayLink") %>">重新玩游戏</a>
</body>
</html>