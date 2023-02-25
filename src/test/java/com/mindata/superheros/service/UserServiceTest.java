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
import java.util.ArrayList;

import static java.lang.String.format;
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
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setEnabled(true);
        user.setRoles(singletonList(authority));

        userService.register(user);

        User newUser = getUserFromDb(username);

        assertThat(newUser.getUsername(), is(username));
        assertThat(newUser.getPassword(), is(encodedPassword));
        assertThat(newUser.getEnabled(), is(true));
        assertThat(newUser.getRoles().get(0).getAuthority(), is(role));
    }

    private User getUserFromDb(String username) {
        String sqlUser = format("select * from users inner join authorities where users.username='%s'", username);
        User user = new User();
        user.setRoles(new ArrayList<>());
        Authority authority = new Authority();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlUser)) {
             while (resultSet.next()) {
                 user.setUsername(resultSet.getString("username"));
                 user.setPassword(resultSet.getString("password"));
                 user.setEnabled(resultSet.getBoolean("enabled"));
                 authority.setUsername(user.getUsername());
                 authority.setAuthority(resultSet.getString("authority"));
                 user.getRoles().add(authority);
             }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
