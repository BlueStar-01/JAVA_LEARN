<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>错误页面</title>
</head>
<body>
<h1>发生错误</h1>
<p>很抱歉，我们遇到了一个错误，无法完成您的请求。</p>
<p>请稍后再试，或<a href="<%= request.getContextPath() %>/cart.jsp">返回主页</a>。</p>
<%
    String errorMessage = (String) session.getAttribute("errorMessage");
    if (errorMessage != null) {
        out.println("<p>" + errorMessage + "</p>");
        session.removeAttribute("errorMessage");
    }
%>
</body>
</html>