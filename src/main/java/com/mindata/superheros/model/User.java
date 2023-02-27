package com.mindata.superheros.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

/**
 * Entity representing a User
 *
 * @since 1.0.0
 */
@Entity(name = "users")
public class User {

    @Id
    @NotBlank(message = "Username should not be null or blank")
    private String username;

    @NotBlank(message = "Password should not be null or blank")
    private String password;

    @NotNull
    private Boolean enabled;

    @OneToMany(mappedBy = "username", cascade = ALL)
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
