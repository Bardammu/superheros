package com.mindata.superheros.service;

import com.mindata.superheros.model.User;
import com.mindata.superheros.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("encodedPassword");
        when(userRepository.saveAndFlush(any(User.class))).thenAnswer(returnsFirstArg());

        userService = new DefaultUserService(userRepository, passwordEncoder);
    }

    @Test
    public void registerNewUser() {
        String username = "john";
        String encodedPassword = "1234";
        String role = "USER";

        User user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);

        User newUser = userService.register(user);

        assertThat(newUser.getUsername(), is(username));
        assertThat(newUser.getPassword(), startsWith("encodedPassword"));
        assertThat(newUser.getEnabled(), is(true));
        assertThat(newUser.getRoles().size(), is(1));
        assertThat(newUser.getRoles().get(0).getUsername(), is(username));
        assertThat(newUser.getRoles().get(0).getAuthority(), is(role));
    }

}
