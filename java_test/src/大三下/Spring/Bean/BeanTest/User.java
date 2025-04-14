package 大三下.Spring.Bean.BeanTest;

import lombok.Data;

@Data
public class User implements Hello {
    private ID id;
    private String message;

    private Integer uid;
    private String name;
    private Integer age;

    @Override
    public String sayHello(String name) {
        return "Hello " + name + "!";
    }
}
