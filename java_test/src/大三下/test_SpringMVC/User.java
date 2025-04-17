package 大三下.test_SpringMVC;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private Integer deptId;
    private String email;
}