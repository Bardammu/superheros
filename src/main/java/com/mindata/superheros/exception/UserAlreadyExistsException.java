package com.mindata.superheros.exception;

import com.mindata.superheros.model.User;

/**
 * Custom exception to be used when there is an attempt to register a {@link User} with a taken username
 *
 * @since 1.0.0
 */
public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
