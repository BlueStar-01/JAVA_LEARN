package test_connections;

import lombok.Data;
import test_jdbc.JDBCUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class JDBCTemplate {
    private DataSource dataSource;


    public JDBCTemplate() {
        try {
            dataSource = new BasicDataSource();
            dataSource.getConnection();
            System.out.println("JDBC连接建立完成");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("建立连接失败");
        }
    }

    public <T> List querForObject(String sql, Class<T> cla, List<Object> args) throws SQLException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        PreparedStatement statement = dataSource.getConnection().prepareStatement(sql);
        List<T> results = new ArrayList<T>();
        for (int i = 0; i < args.size(); i++) {
            statement.setObject(1 + i, args.get(i));
        }
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            T result = cla.getDeclaredConstructor().newInstance();
            int columnCount = resultSet.getMetaData().getColumnCount();
            Field[] fields = cla.getDeclaredFields();
            Map<String, Field> map = new HashMap<>();
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), field);
            }
            for (int i = 0; i < columnCount; i++) {
                String columnName = resultSet.getMetaData().getColumnName(i + 1);
                if (map.containsKey(columnName)) {
                    map.get(columnName).set(result, resultSet.getObject(i + 1));
                }
            }
            results.add(result);
        }
        dataSource.getConnection().close();
        return results;
    }
}