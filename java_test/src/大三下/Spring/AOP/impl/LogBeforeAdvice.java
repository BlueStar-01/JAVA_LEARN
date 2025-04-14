package 大三下.Spring.AOP.impl;


import 大三下.Spring.AOP.BeforeAdvice;

import java.lang.reflect.Method;

public class LogBeforeAdvice implements BeforeAdvice {

    @Override
    public void before(Object target, Method method, Object[] args) {
        System.out.println("执行前日志" + method.getName() + " " + args[0] + " " + target + " 启动了");
    }
}
