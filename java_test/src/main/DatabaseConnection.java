package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseConnection {

    // JDBC URL、用户名和密码
    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/dingding";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "123456";

    public static void main(String[] args) throws SQLException {
        Connection connection = null;

        try {
            // 建立连接
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

            if (connection != null) {
                System.out.println("成功连接到数据库!");
                // 在这里执行数据库操作
            }

        } catch (SQLException e) {
            System.out.println("连接数据库失败: " + e.getMessage());
        } finally {
            // 关闭连接（如果已打开）
            if (connection != null && !connection.isClosed()) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("关闭连接失败: " + e.getMessage());
                }
            }
        }
    }
}