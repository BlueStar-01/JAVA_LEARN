package 大三下.test_connections;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import 大三下.test_jdbc.Book;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Slf4j
public class RunTest {

    public static void main(String[] args) throws SQLException, InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        JDBCTemplate template = new JDBCTemplate();
        String sql = "select * from book where id=?";
        List list = template.querForObject(sql, Book.class, Collections.singletonList(3));
        log.info("非代理查询：\n{}", list);
        template.setDataSource(new DBCPDataSource(template.getDataSource()));
        list = template.querForObject(sql, Book.class, Collections.singletonList(3));
        log.info("代理查询:\n {}", list);
    }
}
