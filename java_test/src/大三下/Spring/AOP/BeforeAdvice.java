package 大三下.Spring.AOP;


import java.lang.reflect.Method;

public interface BeforeAdvice {
    void before(Object target, Method method, Object[] args);
}
