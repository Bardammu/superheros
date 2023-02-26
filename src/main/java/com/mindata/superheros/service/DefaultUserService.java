package com.mindata.superheros.service;

import com.mindata.superheros.exception.UserAlreadyExistsException;
import com.mindata.superheros.model.Authority;
import com.mindata.superheros.model.User;
import com.mindata.superheros.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static java.lang.String.format;
import static java.util.Collections.singletonList;

/**
 * Default implementation of {@link UserService} service
 *
 * @since 1.0.0
 */
@Service
public class DefaultUserService implements UserService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public DefaultUserService(@Autowired UserRepository userRepository, @Autowired PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) {
        if (userRepository.existsById(user.getUsername())) {
            throw new UserAlreadyExistsException(format("User '%s' already exists", user.getUsername()));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        Authority authority = new Authority();
        authority.setUsername(user.getUsername());
        authority.setAuthority("USER");
        user.setRoles(singletonList(authority));
        return userRepository.saveAndFlush(user);
    }
}
