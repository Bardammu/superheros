package com.mindata.superheros.service;

import com.github.fge.jsonpatch.JsonPatch;
import com.mindata.superheros.model.Superhero;

import java.util.List;
import java.util.Map;
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
    Optional<Superhero> getSuperhero(Integer superheroId) throws IllegalArgumentException;

    /**
     * Add a {@link Superhero}
     *
     * @param superhero the {@link Superhero}
     * @return the new {@link Superhero}
     */
    Superhero addSuperhero(Superhero superhero);

    /**
     * Remove a {@link Superhero} with the given {@link Superhero} id
     *
     * @param superheroId the {@link Superhero}
     * @return {@code true} if a {@link Superhero} was removed, {@code false} otherwise
     * @throws IllegalArgumentException if the superhero id is not a valid id
     */
    boolean removeSuperhero(Integer superheroId) throws IllegalArgumentException;

    /**
     * Get the list {@link Superhero Superheros} filtered by the given filtering parameters.
     *
     * @param filteringParameters {@link Map} where its keys are {@link Superhero} attributes and its values substring
     *                            to be used as filters on the attributes.
     * @return a {@link List} of all the filtered {@link Superhero Superheros}
     */
    List<Superhero> getSuperheroFilterBy(Map<String, String> filteringParameters);

    /**
     * Updates a {@link Superhero}
     *
     * @param superhero the modified {@link Superhero}
     * @return the updated {@link Superhero}
     */
    Superhero updateSuperhero(Superhero superhero);

    /**
     * Partially updates a {@link Superhero} using JSON Patch format
     *
     * @param superhero the {@link Superhero} to be updated
     * @param jsonPatch the JSON Patch with the information to updated the {@link Superhero}
     * @return the updated {@link Superhero}
     * @throws IllegalArgumentException if the JSON Patch or the {@link Superhero} is invalid
     *
     * @see <a href=https://datatracker.ietf.org/doc/html/rfc6902/">RFC 6902</a> for more information avout the JSON
     *      Patch format
     */
    Superhero updateSuperhero(Superhero superhero, JsonPatch jsonPatch) throws IllegalArgumentException;
}
