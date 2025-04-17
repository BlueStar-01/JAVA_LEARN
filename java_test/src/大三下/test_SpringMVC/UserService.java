package 大三下.test_SpringMVC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public boolean login(String username, String password) {
        User user = userDao.findByUsername(username);
        return user != null && user.getPassword().equals(password);
    }

    public boolean regist(User user) {
        if (userDao.findByUsername(user.getUsername()) != null) {
            return false; // 用户已存在
        }
        userDao.insert(user);
        return true;
    }

    public void allocDept(Integer userId, Integer deptId) {
        userDao.updateDept(userId, deptId);
    }

    public void updateUser(User user) {
        userDao.update(user);
    }
}