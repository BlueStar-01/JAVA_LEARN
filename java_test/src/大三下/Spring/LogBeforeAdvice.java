package 大三下.Spring;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class LogBeforeAdvice implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("logBeforeAdvice" + method.getName() + " " + args[0] + " " + target + " is called");
    }
}
