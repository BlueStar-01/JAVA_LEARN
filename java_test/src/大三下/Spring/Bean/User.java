package 大三下.Spring.Bean;

import lombok.Data;

@Data
public class User implements Hello {
    private ID id;
    private String message;

    @Override
    public String sayHello(String name) {
        return "Hello " + name + "!";
    }
}
