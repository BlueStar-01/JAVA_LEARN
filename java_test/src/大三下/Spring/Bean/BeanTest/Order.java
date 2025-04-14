package 大三下.Spring.Bean.BeanTest;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Order {
    private String oid;
    private String uid;
    private String orderNum;

}
