package com.mindata.superheros.model.request;

import com.mindata.superheros.model.User;

/**
 * Record that represents an API request containing the {@link User} data
 *
 * @since 1.0.0
 */
public record UserRequest(String username, String password) {
}
