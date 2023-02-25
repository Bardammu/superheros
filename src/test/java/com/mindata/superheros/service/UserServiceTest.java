package com.mindata.superheros.service;

import com.mindata.superheros.model.Authority;
import com.mindata.superheros.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@Sql(scripts = "/sql/testData.sql", executionPhase = BEFORE_TEST_METHOD)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private DataSource dataSource;

    @Test
    public void registerNewUser() {
        String username = "john";
        String encodedPassword = "$2a$10$YMt6Hhiyhzyn5LgWl3gWFuS//w.svL97ZdgWE374HXRl3s6skhM0W";
        String role = "USER";

        Authority authority = new Authority();
        authority.setUsername(username);
        authority.setAuthority(role);

        User user = new User();
        user.setUsername("john");
        user.setPassword(encodedPassword);
        user.setEnabled(true);
        user.setRoles(singletonList(authority));

        userService.register(user);

        User newUser = getUserFromDb(username);

        assertThat(newUser.getUsername(), is(username));
        assertThat(newUser.getUsername(), is(encodedPassword));
        assertThat(newUser.getEnabled(), is(true));
    }

    private User getUserFromDb(String username) {
        User user = new User();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("select * from users where username=" + username)) {
             user.setUsername(resultSet.getString("username"));
             user.setPassword(resultSet.getString("password"));
             user.setEnabled(resultSet.getBoolean("enabled"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
