package 大三下.test_connections;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class JDBCTemplate {
    @Getter
    @Setter
    private static DataSource dataSource;


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

    // 使用ThreadLocal保存连接
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    public static void bindConnection(Connection conn) {
        threadLocal.set(conn);
    }

    public static void unbindConnection() {
        threadLocal.remove();
    }

    // DAO方法获取连接时使用
    public static Connection getConnection() throws SQLException {
        if (threadLocal.get() != null) {
            return threadLocal.get();
        }
        return dataSource.getConnection();
    }

    public <T> List querForObject(String sql, Class<T> cla, List<Object> args) throws SQLException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        PreparedStatement statement = getConnection().prepareStatement(sql);
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

    public int saveForObject(String sql, Class<?> clazz, Object object)
            throws SQLException, IllegalAccessException {

        // 类型安全检查
        if (!clazz.isInstance(object)) {
            throw new IllegalArgumentException("Object is not an instance of " + clazz.getName());
        }

        // 反射获取所有字段
        Field[] fields = clazz.getDeclaredFields();
        List<String> columnNames = new ArrayList<>(fields.length);

        // 提取字段名作为列名（保持与查询方法一致的映射逻辑）
        for (Field field : fields) {
            field.setAccessible(true);
            columnNames.add(field.getName());
        }

        // 自动构建SQL语句
        String columns = String.join(", ", columnNames);
        String placeholders = String.join(", ",
                Collections.nCopies(columnNames.size(), "?"));
        String fullSql = String.format("%s (%s) VALUES (%s)",
                sql.trim(), columns, placeholders);

        // 资源自动关闭（JDK7+ try-with-resources）
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(fullSql)) {

            // 绑定参数值
            for (int i = 0; i < fields.length; i++) {
                Object value = fields[i].get(object);
                stmt.setObject(i + 1, value);
            }

            return stmt.executeUpdate();
        } // 自动关闭连接和statement
    }
}