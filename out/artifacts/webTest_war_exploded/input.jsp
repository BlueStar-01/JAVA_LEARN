<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>猜数字游戏</title>
    <script type="text/javascript">
        // 函数来解析URL参数并返回指定参数的值
        function getUrlParameter(name) {
            name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
            var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
            var results = regex.exec(window.location.search);
            return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
        }

        // 在页面加载时调用此函数
        window.onload = function() {
            var message = getUrlParameter('message');
            if (message) {
                // 获取要显示消息的DOM元素（这里我们创建一个新的<p>元素）
                var messageElement = document.createElement('p');
                messageElement.textContent = message; // 设置文本内容
                messageElement.style.color = 'red'; // 可选：设置文本颜色

                // 将消息元素添加到body的末尾
                document.body.appendChild(messageElement);
            }
        };
    </script>
</head>
<body>
<h1>猜数字游戏</h1>
<form action="GuessNumberServlet" method="post">
    <label for="guess">请输入你的猜测（1-100）：</label>
    <input type="text" id="guess" name="guess">
    <button type="submit">提交</button>
</form>

<!-- 这里不需要额外的元素，因为JavaScript会动态创建一个 -->
</body>
</html>