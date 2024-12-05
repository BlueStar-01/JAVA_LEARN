<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.itheima.Servlet.cart.model.Book" %>
<!DOCTYPE html>
 <html>
<head>
    <title>书籍列表</title>
    <style>
        /* 使整个页面内容居中 */
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            text-align: center; /* 这将使页面中的所有内联元素（如文本和链接）水平居中 */
        }

        /* 包裹表格的容器，用于进一步控制布局 */
        .table-container {
            text-align: left; /* 表格内容应该左对齐 */
            margin: auto; /* 使容器本身水平居中 */
            display: inline-block; /* 允许容器有宽度和边距 */
        }

        table {
            border-collapse: collapse; /* 合并表格边框 */
            width: 80%; /* 设置表格宽度为容器的80% */
            max-width: 800px; /* 限制表格的最大宽度 */
            margin-bottom: 20px; /* 在表格下方添加一些空间 */
        }

        th, td {
            padding: 10px; /* 添加内边距 */
            text-align: left; /* 标题和单元格内容左对齐 */
        }

        img {
            max-width: 100%; /* 图片宽度不超过单元格宽度 */
            height: auto; /* 保持图片比例 */
        }

    </style>
    <script>
        function addToCart(bookId, bookName, bookAuthor, bookIsbn, bookPrice, bookCoverImg) {
            // 在body中创建一个空的div作为容器（如果尚未存在）
            var formContainer = document.getElementById('formContainer');
            if (!formContainer) {
                formContainer = document.createElement('div');
                formContainer.id = 'formContainer';
                document.body.appendChild(formContainer);
            }

            // 创建一个新的表单元素
            var form = document.createElement('form');
            form.action = 'http://127.0.0.1:8080/webTest/cartServlet';  // 确保这个URL是正确的
            form.method = 'post';

            // 创建隐藏的输入字段来存储书籍信息
            var inputs = [
                {name: 'id', value: bookId},
                {name: 'name', value: bookName},
                {name: 'author', value: bookAuthor},
                {name: 'isbn', value: bookIsbn},
                {name: 'coverImg', value: bookCoverImg},
                {name: 'price', value: bookPrice}
            ];

            inputs.forEach(function(input) {
                var inputElement = document.createElement('input');
                inputElement.type = 'hidden';
                inputElement.name = input.name;
                inputElement.value = input.value;
                form.appendChild(inputElement);
            });

            // 将表单添加到容器中
            formContainer.appendChild(form);

            // 提交表单
            form.submit();

            // 提交后移除表单（可选，但推荐，以避免留下无用的DOM元素）
            form.remove();

            // 如果容器中没有其他表单，可以考虑移除容器本身（可选）
            if (!formContainer.hasChildNodes()) {
                formContainer.remove();
            }
        }
    </script>
</head>
<body>
<div class="table-container">
    <h1>Book List</h1>
    <table border="1">
        <tr>
            <th>Image</th>
            <th>Title</th>
            <th>Author</th>
            <th>ISBN</th>
            <th>Price</th>
            <th>Action</th>
        </tr>
        <%
            List<Book> books = (List<Book>) request.getAttribute("books");
            if (books != null) {
                for (Book book : books) {
        %>
        <tr>
            <td><img src="<%= book.getCoverImg() %>" alt="Book Image" width="100" height="150"/></td>
            <td><%= book.getName() %></td>
            <td><%= book.getAuthor() %></td>
            <td><%= book.getIsbn() %></td>
            <td><%= book.getPrice() %></td>
            <td>
                <button onclick="addToCart(<%= book.getId() %>, '<%= book.getName().replace("'", "\\'") %>', '<%= book.getAuthor().replace("'", "\\'") %>', '<%= book.getIsbn().replace("'", "\\'") %>', <%= book.getPrice() %>, '<%= book.getCoverImg().replace("'", "\\'") %>');">
                    添加到购物车
                </button>
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>
    <div>
        <%
            Integer currentPage = (Integer) request.getAttribute("currentPage");
            Integer totalPages = (Integer) request.getAttribute("totalPages");

            if (currentPage != null && currentPage > 1) {
        %>
        <a href="itemServlet?pageNo=<%= currentPage - 1 %>">上一页</a>
        <%
            }
            if (currentPage != null && currentPage < totalPages) {
        %>
        <a href="itemServlet?pageNo=<%= currentPage + 1 %>">下一页</a>
        <%
            }
        %>
        <p>Page <%= currentPage != null ? currentPage.toString() : "N/A" %>
            of <%= totalPages != null ? totalPages.toString() : "N/A" %>
        </p>
    </div>
</div>
<div id="formContainer" style="display:none;"></div> <!-- 这个div实际上不需要隐藏，因为它只是作为容器使用 -->

<br>
</body>
</html>