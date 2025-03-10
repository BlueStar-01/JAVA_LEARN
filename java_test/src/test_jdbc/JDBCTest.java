package test_jdbc;


import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class JDBCTest {
    public static void main(String[] args) throws SQLException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        JDBCTemplate template = new JDBCTemplate();
        String sql = "select * from book where id=?";
        List list = template.querForObject(sql, Book.class, Collections.singletonList(3));
        System.out.println(list);
    }
}
