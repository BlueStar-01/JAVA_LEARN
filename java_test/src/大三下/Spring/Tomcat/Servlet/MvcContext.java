package 大三下.Spring.Tomcat.Servlet;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.stereotype.Controller;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MvcContext {
    /*// 存储 URL路径 -> 处理方法 的映射
    private final Map<String, HandlerMethod> urlMappings = new HashMap<>();

    *//*
     * 初始化MVC上下文：扫描包、注册控制器方法
     *//*
    public void init(String basePackage) {
        // 1. 扫描指定包下所有带有 @Controller注解的类
        Reflections reflections = new Reflections(
                new ConfigurationBuilder()
                        .forPackage(basePackage)
                        .setScanners(Scanners.TypesAnnotated, Scanners.MethodsAnnotated)
        );
        Set<Class<?>> controllerClasses = reflections.getTypesAnnotatedWith(Controller.class);

        // 2. 遍历所有控制器类
        for (Class<?> controllerClass : controllerClasses) {
            processControllerClass(controllerClass);
        }
    }

    *//*
     * 处理单个控制器类：解析类和方法上的@RequestMapping
     *//*
    private void processControllerClass(Class<?> controllerClass) {
        // 1. 获取类级别的@RequestMapping（如果有）
        RequestMapping classMapping = controllerClass.getAnnotation(RequestMapping.class);
        String basePath = (classMapping != null) ? classMapping.value() : "";

        // 2. 遍历类中所有方法
        for (Method method : controllerClass.getDeclaredMethods()) {
            RequestMapping methodMapping = method.getAnnotation(RequestMapping.class);
            if (methodMapping != null) {
                // 3. 拼接完整路径（处理路径格式，如确保以/开头）
                String methodPath = methodMapping.value();
                String fullPath = formatPath(basePath, methodPath);

                // 4. 注册映射关系
                urlMappings.put(fullPath, new HandlerMethod(controllerClass, method));
            }
        }
    }

    *//*
     * 格式化路径：确保路径以/开头，且没有重复斜杠
     *//*
    private String formatPath(String basePath, String methodPath) {
        String path = basePath + methodPath;
        path = path.replaceAll("//+", "/"); // 替换多个/为单个
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        return path;
    }

    *//*
     * 根据URL路径获取对应的处理方法
     *//*
    public HandlerMethod getHandler(String url) {
        return urlMappings.get(url);
    }

    *//*
     * 处理方法元数据（存储控制器类和方法）
     *//*
    public static class HandlerMethod {
        private final Class<?> controllerClass;
        private final Method method;

        public HandlerMethod(Class<?> controllerClass, Method method) {
            this.controllerClass = controllerClass;
            this.method = method;
        }

        // Getters
        public Class<?> getControllerClass() {
            return controllerClass;
        }

        public Method getMethod() {
            return method;
        }
    }*/
}