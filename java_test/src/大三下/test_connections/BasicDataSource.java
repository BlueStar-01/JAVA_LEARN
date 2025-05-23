package 大三下.test_connections;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


@Slf4j
@Data
public class BasicDataSource extends DataSource {

    private String DriverClass = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/test1";
    private String user = "root";
    private String password = "123456";

    // 配置连接池参数（示例）
    public BasicDataSource() {
        try {
            Class.forName(getDriverClass());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        // DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        Connection connection = DriverManager.getConnection(url, user, password);
        log.info("返回非代理连接对象：{}", connection);
        return connection;

    }
}
