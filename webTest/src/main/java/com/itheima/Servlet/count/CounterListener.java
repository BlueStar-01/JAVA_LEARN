package com.itheima.Servlet.count;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.*;
import java.nio.file.*;
import java.util.Properties;

@WebListener
public class CounterListener implements ServletContextListener {

    private static final String COUNTER_PROPERTY_FILE = "/WEB-INF/classes/count.properties";
    private static final String COUNTER_PROPERTY_KEY = "count";
    public static final String COUNTER_CONTEXT_ATTRIBUTE = "counter";


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        Counter counter = loadCounterFromProperties(context);
        context.setAttribute(COUNTER_CONTEXT_ATTRIBUTE, counter);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        Counter counter = (Counter) context.getAttribute(COUNTER_CONTEXT_ATTRIBUTE);
        System.out.println(counter);
        if (counter != null) {
            saveCounterToProperties(context, counter);
        }
    }

    private Counter loadCounterFromProperties(ServletContext context) {
        Properties properties = new Properties();
        try (InputStream input = context.getResourceAsStream(COUNTER_PROPERTY_FILE)) {
            if (input == null) {
                return new Counter(403);
            }
            properties.load(input);
            String initialValue = properties.getProperty(COUNTER_PROPERTY_KEY);
            System.out.println(initialValue);
            int initialCount = (initialValue != null && !initialValue.isEmpty()) ? Integer.parseInt(initialValue) : 403;
            return new Counter(initialCount);
        } catch (IOException ex) {
            ex.printStackTrace();
            return new Counter(403);
        }
    }

    private void saveCounterToProperties(ServletContext context, Counter counter) {
        System.out.println("inside saveCounterToProperties");
        Properties properties = new Properties();
        String sum = String.valueOf(counter.getCount());
        properties.setProperty(COUNTER_PROPERTY_KEY, sum);
        Path path = Paths.get(context.getRealPath(COUNTER_PROPERTY_FILE));
        System.out.println(path);
        System.out.println(sum);
        try (OutputStream output = Files.newOutputStream(path, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
            properties.store(output, "计算机初始值");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}