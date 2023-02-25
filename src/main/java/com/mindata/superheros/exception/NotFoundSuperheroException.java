package com.mindata.superheros.exception;

import com.mindata.superheros.model.Superhero;

import static java.lang.String.format;

/**
 * Custom Exception to be used when there is no {@link Superhero}
 *
 * @since 1.0.0
 */
public class NotFoundSuperheroException extends RuntimeException{

    public NotFoundSuperheroException(Integer superheroId) {
        super(format("Superhero with Id %s not found", superheroId));
    }

    public NotFoundSuperheroException(String name, Integer superheroId) {
        super(format("Superhero %s with Id %s not found", name, superheroId));
    }
}
