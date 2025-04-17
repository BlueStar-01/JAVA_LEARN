package 大三下.Spring.Tomcat;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import 大三下.Spring.Tomcat.Servlet.DispatcherServlet;

import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class EmbeddedTomcatServer {
    public static void start() throws ServletException, LifecycleException {
        try {
            // 1. 创建 Tomcat 实例
            Tomcat tomcat = new Tomcat();
            tomcat.setPort(8080); // 设置端口

            // 2. 创建临时工作目录
            File baseDir = Files.createTempDirectory("tomcat-base").toFile();
            tomcat.setBaseDir(baseDir.getAbsolutePath());

            // 3. 创建上下文路径
            String contextPath = "";
            String docBase = new File(".").getAbsolutePath();
            StandardContext ctx = (StandardContext) tomcat.addContext(contextPath, docBase);


            //todo 可以通过配置文件或者注解解析

            // 4. 创建并注册 DispatcherServlet
            DispatcherServlet dispatcherServlet = new DispatcherServlet();

            Wrapper servletWrapper = Tomcat.addServlet(ctx, "BLueStar", dispatcherServlet);
            servletWrapper.addMapping("/*"); // 映射所有请求
            servletWrapper.setLoadOnStartup(1);

            // 5. 启动服务器
            tomcat.start();
            tomcat.getServer().await(); // 阻塞主线程，等待请求
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}