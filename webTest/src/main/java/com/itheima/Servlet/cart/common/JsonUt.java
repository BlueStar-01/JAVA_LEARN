package com.itheima.Servlet.cart.common;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JsonUt {
    public static void JSONtoResp(HttpServletResponse resp, Object result) throws IOException {
        System.out.println(result);
        // 设置响应内容类型为JSON
        resp.setContentType("application/json;charset=UTF-8");

        // 使用 Jackson 将对象转换为 JSON 字符串
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(result);

        // 将 JSON 字符串写入响应体
        resp.getWriter().write(jsonString);
    }
}
