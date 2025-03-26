package 大三下.MyBaits;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.jupiter.api.*;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import 大三下.MyBaits.mapper.StudentMapper;
import 大三下.MyBaits.pojo.Student;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MyBaitsTest {

    private static StudentMapper mapper;
    private static Student student = new Student();
    private static SqlSessionFactory sqlSessionFactory = initSqlSessionFactory();
    private static SqlSession sqlSession;


    public static SqlSessionFactory initSqlSessionFactory() {
        DataSource dataSource = dataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("Production", transactionFactory, dataSource);
        MybatisConfiguration configuration = new MybatisConfiguration(environment);
        //配置数据主键插入返回
        configuration.setUseGeneratedKeys(true);
        //在这里添加Mapper
        configuration.addMapper(StudentMapper.class);
        configuration.setLogImpl(StdOutImpl.class);
        return new MybatisSqlSessionFactoryBuilder().build(configuration);
    }

    public static DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost:3306/test1");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        try {
            Connection connection = dataSource.getConnection();

            // 测试方法1：执行简单查询验证连接有效性
            Statement stmt = connection.createStatement();
            stmt.execute("SELECT 1"); // 执行简单查询
            System.out.println("数据库连接成功！");

            // 测试方法2：检查连接对象是否有效
            if (connection.isValid(0)) { // 参数0表示不等待直接检查
                System.out.println("连接状态有效");
            }

            // 释放资源
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println("数据库连接失败: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return dataSource;
    }


    @BeforeEach
    public void Before() {
        System.out.println("Before: Student:" + student);
        sqlSession = sqlSessionFactory.openSession();
        mapper = sqlSession.getMapper(StudentMapper.class);
    }

    @AfterEach
    public void After() {
        System.out.println("After: Student:" + student);
        sqlSession.commit();
        System.out.println(student);
    }

    @Test
    @Order(1)
    public void 增() {
        //增
        System.out.println("增------------------------------------");
        student.setSname("碧蓝小新星");
        student.setAge(22);
        Student byId = mapper.selectById(7);
        Assertions.assertNotNull(byId);
        log.info("查找数据id为7：" + byId);
        student.setSid(null);
        int insert = mapper.insert(student);
        Assertions.assertTrue(insert > 0);
    }

    @Test
    @Order(2)
    public void 改() {
        //改
        System.out.println("改------------------------------------");
        student = mapper.selectById(student.getSid());
        Assertions.assertNotNull(student);
        student.setSname("碧蓝小新星2");
        mapper.updateById(student);
    }

    @Test
    @Order(3)

    public void 查() {
        //查
        System.out.println("查------------------------------------");
        Student byId = mapper.selectById(student.getSid());
        Assertions.assertNotNull(byId);
        log.info(byId.toString());
    }

    @Test
    @Order(4)
    public void 删() {
        //删
        System.out.println("删------------------------------------");
        Assertions.assertNotNull("id应该不为空", String.valueOf(student.getSid()));
        int i = mapper.deleteById(student.getSid());
        Assertions.assertTrue(i > 0);
    }


}
