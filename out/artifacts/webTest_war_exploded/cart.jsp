<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>购物车</title>
</head>
<body>
<h1>购物车内容</h1>
<ul>
    <c:forEach var="item" items="${pagedItems}">
        <li>${item.name} - ${item.quantity} 个</li>
    </c:forEach>
</ul>

<div>
    <c:if test="${currentPage > 1}">
        <a href="?page=${currentPage - 1}">上一页</a>
    </c:if>
    <c:forEach begin="1" end="${totalPages}" var="page">
        <c:choose>
            <c:when test="${page == currentPage}">
                <strong>${page}</strong>
            </c:when>
            <c:otherwise>
                <a href="?page=${page}">${page}</a>
            </c:otherwise>
        </c:choose>
    </c:forEach>
    <c:if test="${currentPage < totalPages}">
        <a href="?page=${currentPage + 1}">下一页</a>
    </c:if>
</div>
</body>
</html>