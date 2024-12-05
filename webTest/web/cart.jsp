<%@ page import="java.util.List" %>
<%@ page import="com.itheima.Servlet.cart.model.Book" %> <%-- 替换为您的Book类所在的包 --%>
<%@ page import="com.itheima.Servlet.cart.model.Cart" %> <%-- 替换为您的Cart类所在的包 --%>
<%@ page import="com.itheima.Servlet.cart.common.AppConfigConstants" %> <%-- 替换为您的常量类所在的包 --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>购物车</title>
</head>
<body>
<h1>购物车</h1>
<%
    // 从会话中获取购物车对象
    Cart cart = (Cart) session.getAttribute(AppConfigConstants.CART_KEY);
    if (cart != null) {
        List<Cart.CartRow> items = cart.getAllRow();
        if (!items.isEmpty()) {
            out.println("<table border='1'>");
            out.println("<tr><th>ID</th><th>名称</th><th>作者</th><th>ISBN</th><th>价格</th><th>封面图片</th><th>操作</th><th>数量</th></tr>");
            for (Cart.CartRow row : items) {
                out.println("<td>" + row.getItem().getId() + "</td>");
                out.println("<td>" + row.getItem().getName() + "</td>");
                out.println("<td>" + row.getItem().getAuthor() + "</td>");
                out.println("<td>" + row.getItem().getIsbn() + "</td>");
                out.println("<td>" + row.getItem().getPrice() + "</td>");
                out.println("<td><img src='" + row.getItem().getCoverImg() + "' alt='封面' style='width:100px;height:150px;'></td>");
                out.println("<td>" + row.getNumber() + "</td>");
                // 假设有一个删除商品的链接或按钮
                out.println("<td><a href='removeFromCart?id=" + row.getItem().getId() + "'>删除</a></td>");
                out.println("</tr>");
            }
            out.println("</table>");
        } else {
            out.println("<p>购物车是空的。</p>");
        }
    } else {
        out.println("<p>会话已过期或购物车不存在。</p>");
    }
%>
<a href="<%= request.getContextPath() +AppConfigConstants.ITEM_SERVLET_URL+"?pageNo=1"%>">继续购物</a>
<a href="<%= request.getContextPath() +AppConfigConstants.SUCCESS_JSP_URL%>">结算</a>
</body>
</html>