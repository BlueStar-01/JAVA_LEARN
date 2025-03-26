package da3xiao.test_connections;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DataSource {
    public abstract Connection getConnection() throws SQLException;
}
