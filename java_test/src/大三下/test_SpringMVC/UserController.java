package 大三下.test_SpringMVC;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")  // 允许所有来源的跨域请求
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password
    ) {
        return userService.login(username, password)
                ? "登录成功" : "用户名或密码错误";
    }

    @PostMapping("/regist")
    public String regist(@Validated @RequestBody User user) {
        return userService.regist(user)
                ? "注册成功" : "用户名已存在";
    }

    @PostMapping("/allocDept")
    public String allocDept(
            @RequestParam Integer userId,
            @RequestParam Integer deptId
    ) {
        userService.allocDept(userId, deptId);
        return "部门分配成功";
    }

    @PostMapping("/updateUser")
    public String updateUser(@Validated @RequestBody User user) {
        userService.updateUser(user);
        return "用户信息更新成功";
    }
}