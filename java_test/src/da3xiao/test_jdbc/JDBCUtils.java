package da3xiao.test_jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtils {

    private static final String name = "root";
    private static final String password = "123456";

    public JDBCUtils() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.printf("未找到驱动类");
            e.printStackTrace();
        }

    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/dingding", name, password);
    }

    public static boolean closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException s) {
            return false;
        }
        return true;
    }
}