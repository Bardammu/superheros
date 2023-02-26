package com.mindata.superheros.model.response;

import com.mindata.superheros.model.User;

import java.util.List;

/**
 * Record that represents an API response containing the {@link User} data
 */
public record UserResponse(String username, Boolean enabled, List<String> roles) {
}
