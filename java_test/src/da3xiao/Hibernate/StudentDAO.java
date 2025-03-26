package da3xiao.Hibernate;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class StudentDAO {
    public static void main(String[] args) {
        StudentDAO dao = new StudentDAO();

        // 添加学生
        Student student = new Student();
        student.setSname("张三");
        student.setAge(20);
        dao.saveStudent(student);
        System.out.println("保存成功，ID: " + student.getSid());

        // 查询学生
        Student s = dao.getStudentById(1);
        System.out.println("查询结果: " + s.getSname());

        // 更新学生
        s.setAge(21);
        dao.updateStudent(s);

        // 删除学生
        dao.deleteStudent(1);
    }

    public void saveStudent(Student student) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(student);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public Student getStudentById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Student.class, id);
        }
    }

    public void deleteStudent(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Student student = session.get(Student.class, id);
            if (student != null) {
                session.delete(student);
                transaction.commit();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    public void updateStudent(Student student) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(student);
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}