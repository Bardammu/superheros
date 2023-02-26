package com.mindata.superheros.exception;

import com.mindata.superheros.model.Superhero;

/**
 * Custom Exception to be used when there is no {@link Superhero}
 *
 * @since 1.0.0
 */
public class NotFoundSuperheroException extends RuntimeException {

    public NotFoundSuperheroException(String message) {
        super(message);
    }

}

