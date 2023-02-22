package com.mindata.superheros.service;

import com.mindata.superheros.model.Superhero;

import java.util.List;
import java.util.Optional;

/**
 * Service to manage a catalog of superheros
 *
 * @since 1.0.0
 */
public interface SuperheroService {

    /**
     * Get a lists of all superheros
     *
     * @return a {@link List} of all {@link Superhero Superheros}
     */
    List<Superhero> getSuperheroes();

    /**
     * Get a {@link Superhero} with the given Superhero id if present
     *
     * @param superheroId the Superhero's id
     * @return a {@link Superhero} if the {@link Superhero} id is present, an empty {@link Optional} otherwise
     * @throws IllegalArgumentException if the superhero id is not a valid id
     */
    Optional<Superhero> getSuperhero(Integer superheroId);

    /**
     * Add a {@link Superhero}
     * @param superhero the {@link Superhero}
     */
    void addSuperhero(Superhero superhero);

    /**
     * Remove a {@link Superhero} with the given {@link Superhero} id
     *
     * @param superheroId the {@link Superhero}
     * @return {@code true} if a {@link Superhero} was removed, {@code false} otherwise
     * @throws IllegalArgumentException if the superhero id is not a valid id
     */
    boolean removeSuperhero(Integer superheroId);

    /**
     * Get the list {@link Superhero Superheros} that contains the given sub String on the given attribute
     *
     * @param attribute the {@link Superhero}'s attribute to be use on the filter
     * @param subString the {@link String} to filter by
     * @return a {@link List} of all {@link Superhero Superheros} that contains the given sub String on the given
     *         attribute
     */
    List<Superhero> getSuperheroFilterBy(String attribute, String subString);
}
