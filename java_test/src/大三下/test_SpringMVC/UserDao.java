package 大三下.test_SpringMVC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User findByUsername(String username) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM user WHERE username = ?",
                    new BeanPropertyRowMapper<>(User.class),
                    username
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void insert(User user) {
        jdbcTemplate.update(
                "INSERT INTO user (username, password, email) VALUES (?, ?, ?)",
                user.getUsername(), user.getPassword(), user.getEmail()
        );
    }

    public void updateDept(Integer userId, Integer deptId) {
        jdbcTemplate.update(
                "UPDATE user SET dept_id = ? WHERE id = ?",
                deptId, userId
        );
    }

    public void update(User user) {
        jdbcTemplate.update(
                "UPDATE user SET password = ?, email = ? WHERE id = ?",
                user.getPassword(), user.getEmail(), user.getId()
        );
    }
}