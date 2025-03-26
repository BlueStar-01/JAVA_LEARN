package da3xiao.test_connections;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
public class DBCPDataSource extends DataSource {
    private DataSource con;

    public DBCPDataSource(DataSource con) {
        this.con = con;
    }

    @Override
    public Connection getConnection() throws SQLException {
        Connection connection = (Connection) Proxy.newProxyInstance(
                Connection.class.getClassLoader(),
                new Class[]{Connection.class},
                new ConnectionHandler(con.getConnection()));
        log.info("返回代理连接对象：{}", connection);
        return connection;
    }
}
