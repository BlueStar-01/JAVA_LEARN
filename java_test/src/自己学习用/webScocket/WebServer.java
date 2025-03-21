package 自己学习用.webScocket;

import javax.servlet.Servlet;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.io.*;
import java.util.concurrent.*;

public class WebServer {
    private static final int PORT = 8080;
    private static final int THREAD_POOL_SIZE = 10;
    private static final Map<String, String> routeMap = new HashMap<>();

    static {
        routeMap.put("/api", "自己学习用.webScocket.ApiServlet");
        routeMap.put("/image", "自己学习用.webScocket.ImageServlet");
    }

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("服务开始监听" + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                threadPool.execute(() -> handleRequest(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleRequest(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            // 解析HTTP请求URI
            String requestLine = in.readLine();
            if (requestLine == null) return;

            String[] requestParts = requestLine.split(" ");
            if (requestParts.length < 3) return;

            String uri = requestParts[1];

            // 处理图标请求
            if (uri.contains("/favicon.ico")) {
                sendIconResponse(out);  // 专用图标处理方法
                return;
            }

            String servletClassName = routeMap.get(uri);
            if (servletClassName != null) {
                // 动态构建Servlet
                Class<?> servletClass = Class.forName(servletClassName);
                Servlet servlet = (Servlet) servletClass.getDeclaredConstructor().newInstance();

                // 构建UTF-8响应
                String responseBody = buildSimpleResponse(servlet);  // 简化响应内容生成
                sendUTF8Response(out, responseBody);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // UTF-8响应生成方法
    private static void sendUTF8Response(PrintWriter out, String content) {
        byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);
        out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: text/html; charset=utf-8");
        out.println("Content-Length: " + contentBytes.length);
        out.println("Connection: close");
        out.println();
        out.print(content);
    }

    // 图标响应处理方法
    private static void sendIconResponse(PrintWriter out) {
        out.println("HTTP/1.1 200 ok");
        out.println("Location: D:\\myworkspace-java\\JAVA_LEARN\\java_test\\src\\webScocket\\ico.png");  // 重定向到指定图标路径
        out.println();
    }

    // 简化响应体生成
    private static String buildSimpleResponse(Servlet servlet) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<meta charset='UTF-8'>" +
                "<title>Servlet信息</title>" +
                "</head>" +
                "<body>" +
                "<h1>Servlet基本信息</h1>" +
                "<p>" + servlet.getServletInfo() + "</p>" +
                "</body>" +
                "</html>";
    }
}
