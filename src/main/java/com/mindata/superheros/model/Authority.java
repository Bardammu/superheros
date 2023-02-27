package com.mindata.superheros.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Entity representing the rol of a {@link User}
 *
 * @since 1.0.0
 */
@Entity(name = "authorities")
public class Authority {

    @Id
    private String username;

    @NotNull
    @Size(max = 10)
    private String authority;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
