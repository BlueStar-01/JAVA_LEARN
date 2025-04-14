package 大三下.Spring.Bean.Server;

import 大三下.Spring.Bean.BeanTest.Order;

import java.sql.SQLException;
import java.util.List;

public interface Service {
    Boolean saveOrder(Order order) throws SQLException, IllegalAccessException;
    Boolean saveOrder(List<Order> order) ;
}
