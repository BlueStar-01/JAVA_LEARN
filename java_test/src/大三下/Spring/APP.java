package 大三下.Spring;


import java.io.File;

public class APP {

    public static void run() throws Exception {
        XmlBeanFactory xmlBeanFactory = new XmlBeanFactory();
        xmlBeanFactory.init(new File("java_test/src/大三下/Spring/applicationContext.xml"));

        User user = (User) xmlBeanFactory.getBean("user");
        System.out.println(user.sayHello("tom"));
    }

    public static void main(String[] args) throws Exception {
        APP.run();
    }
}
