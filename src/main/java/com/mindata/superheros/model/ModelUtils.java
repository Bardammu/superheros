package com.mindata.superheros.model;

/**
 * Utility class ta helps converting {@link SuperheroResponse} to {@link Superhero} and vice versa
 */
public class ModelUtils {

    public static Superhero getSuperheroFromRequest(SuperheroRequest superheroRequest) {
        Superhero superhero = new Superhero();
        superhero.setId(superheroRequest.getId());
        superhero.setName(superheroRequest.getName());
        superhero.setGender(superheroRequest.getGender());
        superhero.setOrigin(superheroRequest.getOrigin());
        superhero.setBirthdate(superheroRequest.getBirthdate());
        return superhero;
    }

    public static SuperheroResponse getSuperheroResponseFromSuperhero(Superhero superhero) {
        SuperheroResponse superheroResponse = new SuperheroResponse();
        superheroResponse.setId(superhero.getId());
        superheroResponse.setName(superhero.getName());
        superheroResponse.setGender(superhero.getGender());
        superheroResponse.setOrigin(superhero.getOrigin());
        superheroResponse.setBirthdate(superhero.getBirthdate());
        return superheroResponse;
    }
}
