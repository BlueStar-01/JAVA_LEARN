package 大三下.Spring.Bean.Server;


import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import 大三下.Spring.Bean.BeanTest.Order;
import 大三下.test_connections.JDBCTemplate;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Slf4j
public class OrderService implements Service {

    @Setter
    private JDBCTemplate jdbcTemplate;

    @Override
    public Boolean saveOrder(Order order) throws SQLException, IllegalAccessException {
        try {
            //查找对应的用户在不在
            List list = jdbcTemplate.querForObject("select count(*) conut from user0 where uid = ?",
                    order.getClass(), Collections.singletonList(order.getUid()));

            if (list.size() <= 0) {
                log.info("用户不存在");
                throw new RuntimeException("用户不存在");
            }

        } catch (Exception e) {
            log.info("查找异常");
            e.printStackTrace();
        }
        //再执行对应的插入操作
        jdbcTemplate.saveForObject("insert into order0 ", order.getClass(), order);
        return true;
    }

    public Boolean saveOrder(List<Order> order) {
        Boolean b = true;
        order.forEach((o) -> {
            try {
                if (!saveOrder(o)) {
                    log.info("插入失败{}", o);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        return b;
    }


}
