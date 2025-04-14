package 大三下.Spring.AOP.impl;

import lombok.Setter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import 大三下.Spring.AOP.AroundAdvice;
import 大三下.test_connections.JDBCTemplate;

import java.sql.Connection;

@Setter
public class TransactionAroundAdvice implements AroundAdvice {

    private static final Logger log = LoggerFactory.getLogger(TransactionAroundAdvice.class);
    // 通过JDBCTemplate获取数据源
    private JDBCTemplate dataSource;


    @Override
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        Connection conn = null;
        try {
            log.info("事务开启");
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);

            // 执行目标方法
            Object result = pjp.proceed();  // 必须调用proceed()

            conn.commit();
            log.info("事务完成提交");
            return result;
        } catch (Throwable e) {
            if (conn != null) conn.rollback();
            log.info("事务执行失败，已回滚 {}", e.getMessage());
            // throw new RuntimeException("事务执行失败，已回滚");
        } finally {
            if (conn != null) conn.close();
        }
        return null;
    }
}
