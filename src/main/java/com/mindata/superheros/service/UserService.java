package com.mindata.superheros.service;

import com.mindata.superheros.model.User;

/**
 * Service to manage a {@link User users}
 *
 * @since 1.0.0
 */
public interface UserService {

    /**
     * Register a new user
     *
     * @param user the new {@link User}
     * @return the new created {@link User}
     */
    User register(User user);

}
