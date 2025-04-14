package 大三下.Spring.Bean.BeanTest;

import lombok.Data;

@Data
public class User implements Hello {
    private ID id;
    private String message;

    private String uid;
    private String name;
    private int age;

    @Override
    public String sayHello(String name) {
        return "Hello " + name + "!";
    }
}
