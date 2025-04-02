package 大三下.Spring;



import java.lang.reflect.Method;

public class LogBeforeAdvice implements BeforeAdvice {

    @Override
    public void before(Object target, Method method, Object[] args) {
        System.out.println("logBeforeAdvice" + method.getName() + " " + args[0] + " " + target + " is called");
    }
}
