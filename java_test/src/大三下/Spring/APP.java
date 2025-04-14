package 大三下.Spring;


import org.apache.ibatis.annotations.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import 大三下.Spring.Bean.BeanTest.Hello;
import 大三下.Spring.Bean.BeanTest.Order;
import 大三下.Spring.Bean.Server.OrderService;
import 大三下.Spring.Bean.Server.Service;
import 大三下.Spring.Factory.XmlBeanFactory;

import java.io.File;
import java.util.List;

public class APP {

    private static final Logger log = LoggerFactory.getLogger(APP.class);

    public static void run() throws Exception {
        XmlBeanFactory xmlBeanFactory = new XmlBeanFactory();
        xmlBeanFactory.init(new File("java_test/src/大三下/Spring/applicationContext.xml"));

        Hello user = (Hello) xmlBeanFactory.getBean("user");
        System.out.println(user.sayHello("tom"));

        System.out.println(user);

        Service orderService = (Service) xmlBeanFactory.getBean("orderService");
        //成功订单
        Order order1 = new Order()
                .setOid("1")
                .setUid("1")
                .setOrderNum("1");
        //失败订单
        Order order2 = new Order()
                .setOid("2")
                .setUid("2")
                .setOrderNum("2");

        orderService.saveOrder(List.of(order1, order2));

        log.info("不中断主程序的使用");

    }

    public static void main(String[] args) throws Exception {
        APP.run();
    }
}
