package com.mindata.superheros.model.request;

import com.mindata.superheros.model.Superhero;

import java.sql.Date;

/**
 * Record that represents an API request containing the {@link Superhero} data
 */
public record SuperheroRequest(Integer id, String name, String gender, String origin, Date birthdate) {
}
