package 大三下.test_connections;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Stack;

@Slf4j
public class ConnectionHandler implements InvocationHandler {
    private Connection targetConn;
    private static final Stack<Connection> connectionPool = new Stack<>();

    public ConnectionHandler(Connection targetConn) {
        this.targetConn = targetConn;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //拦截Close方法
        method.setAccessible(true);
        if (method.getName().equals("close")) {
            //返回连接池
            connectionPool.push(targetConn);
            log.info("代理类拦截关闭方法,连接池剩余连接数量{}", connectionPool.size());
            return null;
        }
        log.info("非关闭方法，放行：{}", method.getName());
        return method.invoke(targetConn, args);
    }
}
