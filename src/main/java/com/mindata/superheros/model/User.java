package com.mindata.superheros.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * Entity representing a User
 *
 * @since 1.0.0
 */
@Entity(name = "users")
public class User {

    @Id
    @NotBlank(message = "Username should not be null or blank")
    @Size(min = 4, max = 10, message = "Username shouldn't be longer than 10 characters")
    private String username;

    @NotBlank(message = "Password should not be null or blank")
    @Size(max = 100)
    private String password;

    @NotNull
    private Boolean enabled;

    @OneToMany(mappedBy = "username")
    private List<Authority> roles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<Authority> getRoles() {
        return roles;
    }

    public void setRoles(List<Authority> roles) {
        this.roles = roles;
    }
}
