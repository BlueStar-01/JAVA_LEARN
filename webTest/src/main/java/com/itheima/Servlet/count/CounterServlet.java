package com.itheima.Servlet.count;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(
        urlPatterns = {"/counter"},
        initParams = {
                @WebInitParam(name = "counter", value = "5")
        }
)
public class CounterServlet extends HttpServlet {
    public static final String COUNTER_CONTEXT_ATTRIBUTE = "counter";
    public static final String COUNTER_PROPERTY_FILE = "/WEB-INF/classes/main/resources/count.properties";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
//        ServletContext context = getServletContext();
//
//        // 读取count.properties文件中的初始值
//        Properties properties = new Properties();
//        try (InputStream input = context.getResourceAsStream(COUNTER_PROPERTY_FILE)) {
//            if (input != null) {
//                properties.load(input);
//                String initialCount = properties.getProperty("count");
//                int count = (initialCount != null && !initialCount.isEmpty()) ? Integer.parseInt(initialCount) : 0;
//
//                // 创建Counter对象并放入ServletContext
//                Counter counter = new Counter(count);
//                context.setAttribute(COUNTER_CONTEXT_ATTRIBUTE, counter);
//            } else {
//                System.out.println("Counter property file not found");
//                // 如果找不到文件，则使用默认计数值0
//                Counter counter = new Counter(403);
//                context.setAttribute(COUNTER_CONTEXT_ATTRIBUTE, counter);
//            }
//        } catch (IOException e) {
//            throw new ServletException("读取count.properties文件时出错", e);
//        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        Counter counter = (Counter) context.getAttribute(COUNTER_CONTEXT_ATTRIBUTE);

        // 增加计数器值
        counter.add();

        // 将计数器值显示到页面上（或者您可以选择其他方式处理它）
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>count: " + counter.getCount() + "</h1>");
        out.println("</body></html>");
    }

    @Override
    public void destroy() {
//        ServletContext context = getServletContext();
//        Counter counter = (Counter) context.getAttribute(COUNTER_CONTEXT_ATTRIBUTE);
//
//        if (counter != null) {
//            Properties properties = new Properties();
//            properties.setProperty("count", String.valueOf(counter.getCount()));
//
//            Path filePath = Paths.get(context.getRealPath(COUNTER_PROPERTY_FILE));
//            try (OutputStream output = Files.newOutputStream(filePath, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
//                properties.store(output, "计数器初始值");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        super.destroy();
    }
}