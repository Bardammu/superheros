package com.mindata.superheros.model.response;

import com.mindata.superheros.model.Superhero;

import java.sql.Date;

/**
 * Record that represents an API response containing the {@link Superhero} data
 */
public record SuperheroResponse(Integer id, String name, String gender, String origin, Date birthdate) {
}
