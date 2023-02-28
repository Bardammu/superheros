package com.mindata.superheros.model;

import com.mindata.superheros.model.request.AddSuperheroRequest;
import com.mindata.superheros.model.request.UpdateSuperheroRequest;
import com.mindata.superheros.model.request.UserRequest;
import com.mindata.superheros.model.response.SuperheroResponse;
import com.mindata.superheros.model.response.UserResponse;

import java.sql.Date;
import java.util.List;

/**
 * Utility class that helps to convert {@link SuperheroResponse} to {@link Superhero} and vice versa,
 * {@link UserRequest} to {@link User} and vice versa.
 */
public class ModelUtils {

    public static Superhero getSuperheroFromRequest(AddSuperheroRequest addSuperheroRequest) {
        Superhero superhero = new Superhero();
        superhero.setName(addSuperheroRequest.name());
        superhero.setGender(addSuperheroRequest.gender());
        superhero.setOrigin(addSuperheroRequest.origin());
        superhero.setBirthdate(addSuperheroRequest.birthdate());
        return superhero;
    }

    public static Superhero getSuperheroFromRequest(UpdateSuperheroRequest updateSuperheroRequest) {
        Superhero superhero = new Superhero();
        superhero.setId(updateSuperheroRequest.id());
        superhero.setName(updateSuperheroRequest.name());
        superhero.setGender(updateSuperheroRequest.gender());
        superhero.setOrigin(updateSuperheroRequest.origin());
        superhero.setBirthdate(updateSuperheroRequest.birthdate());
        return superhero;
    }

    public static SuperheroResponse getSuperheroResponseFromSuperhero(Superhero superhero) {
        Integer superheroId = superhero.getId();
        String name = superhero.getName();
        String gender = superhero.getGender();
        String origin = superhero.getOrigin();
        Date birhdate = superhero.getBirthdate();
        return new SuperheroResponse(superheroId, name, gender, origin, birhdate);
    }

    public static User getUserFromRequest(UserRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.username());
        user.setPassword(userRequest.password());
        return user;
    }

    public static UserResponse getUserResponseFromUser(User user) {
        String username = user.getUsername();
        Boolean enabled = user.getEnabled();
        List<String> roles = user.getRoles().stream().map(Authority::getAuthority).toList();
        return new UserResponse(username, enabled, roles);
    }
}
