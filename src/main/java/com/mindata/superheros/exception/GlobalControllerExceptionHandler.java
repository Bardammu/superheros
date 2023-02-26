package com.mindata.superheros.exception;

import com.mindata.superheros.model.response.SimpleMessageResponse;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

    Logger logger = getLogger(GlobalControllerExceptionHandler.class);

    @ExceptionHandler(NotFoundSuperheroException.class)
    public ResponseEntity<SimpleMessageResponse> handleSuperheroNotFound(NotFoundSuperheroException ex, ServletWebRequest request) {
        logger.error("Request {} raided{}", request.getRequest().getRequestURI(), ex);

        return new ResponseEntity<>(new SimpleMessageResponse("Superhero not found"), NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<SimpleMessageResponse> handIllegalArgumentException(IllegalArgumentException ex, ServletWebRequest request) {
        logger.error("Request {} raided{}", request.getRequest().getRequestURI(), ex);

        return new ResponseEntity<>(new SimpleMessageResponse(ex.getMessage()), BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<SimpleMessageResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex, ServletWebRequest request) {
        logger.error("Request {} raided{}", request.getRequest().getRequestURI(), ex);

        return new ResponseEntity<>(new SimpleMessageResponse(ex.getMessage()), CONFLICT);
    }

}
