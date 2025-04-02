package 大三下.Spring;


import 大三下.Spring.Bean.Hello;
import 大三下.Spring.Bean.User;
import 大三下.Spring.Factory.XmlBeanFactory;

import java.io.File;

public class APP {

    public static void run() throws Exception {
        XmlBeanFactory xmlBeanFactory = new XmlBeanFactory();
        xmlBeanFactory.init(new File("java_test/src/大三下/Spring/applicationContext.xml"));

        Hello user = (Hello) xmlBeanFactory.getBean("user");
        System.out.println(user.sayHello("tom"));

        System.out.println(user);

    }

    public static void main(String[] args) throws Exception {
        APP.run();
    }
}
