package com.mindata.superheros.integration;

import com.mindata.superheros.model.request.UserRequest;
import com.mindata.superheros.model.response.SimpleMessageResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON;

public class UserITCase extends IntegrationITCase {

    @Test
    public void registerNewUser() {
        UserRequest userRequest = new UserRequest("john", "1234");

        HttpEntity<UserRequest> requestSuperheroEntity = new HttpEntity<>(userRequest);
        ResponseEntity<SimpleMessageResponse> responseEntity = restTemplate
                .postForEntity(USER_REGISTER_URL, requestSuperheroEntity , SimpleMessageResponse.class);

        assertThat(responseEntity.getStatusCode(), is(CREATED));
        assertThat(responseEntity.getHeaders().getContentType(), is(APPLICATION_JSON));
    }

    @Test
    public void registerUserWithTakenUsername() {
        UserRequest userRequest = new UserRequest("user", "1234");

        HttpEntity<UserRequest> requestSuperheroEntity = new HttpEntity<>(userRequest);
        ResponseEntity<SimpleMessageResponse> responseEntity = restTemplate
                .postForEntity(USER_REGISTER_URL, requestSuperheroEntity , SimpleMessageResponse.class);

        assertThat(responseEntity.getStatusCode(), is(CONFLICT));
        assertThat(responseEntity.getHeaders().getContentType(), is(APPLICATION_JSON));
        assertThat(Objects.requireNonNull(responseEntity.getBody()).message(), is("User 'user' already exists"));
    }
}
