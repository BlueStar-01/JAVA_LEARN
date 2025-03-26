package da3xiao.Hibernate;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;

public class HibernateUtil {
    @Getter
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure(new File("java_test/src/Hibernate/hibernate.cfg.xml")).buildSessionFactory();
        } catch (Exception e) {
            System.err.println("初始化SessionFactory失败: " + e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}