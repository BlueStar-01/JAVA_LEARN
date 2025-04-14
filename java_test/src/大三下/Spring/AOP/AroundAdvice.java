package 大三下.Spring.AOP;

import org.aspectj.lang.ProceedingJoinPoint;

import java.sql.SQLException;


public interface AroundAdvice {
     Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable;
}
