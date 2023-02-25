package com.mindata.superheros.service;

import com.mindata.superheros.model.User;
import com.mindata.superheros.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link UserService} service
 *
 * @since 1.0.0
 */
@Service
public class DefaultUserService implements UserService{

    private final UserRepository userRepository;

    public DefaultUserService(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void register(User user) {
        userRepository.saveAndFlush(user);
    }
}
