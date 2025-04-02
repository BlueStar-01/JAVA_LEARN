package 大三下.Spring;


import java.io.File;

public class APP {

    public static void run(String[] args) throws Exception {
        XmlBeanFactory xmlBeanFactory = new XmlBeanFactory();
        xmlBeanFactory.init(new File("applicationContext.xml"));

        User user = (User) xmlBeanFactory.getBean("user");
        System.out.println(user.sayHello("tom"));


    }

    public static void main(String[] args) {

    }
}
