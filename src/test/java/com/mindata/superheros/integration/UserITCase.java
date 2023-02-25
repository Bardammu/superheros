package com.mindata.superheros.integration;

import com.mindata.superheros.model.SuperheroResponse;
import com.mindata.superheros.model.UserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON;

public class UserITCase extends IntegrationITCase {

    @Test
    public void registerNewUser() {
        String url = USER_URL + "/register";
        UserRequest userRequest = new UserRequest("john", "1234");

        HttpEntity<UserRequest> requestSuperheroEntity = new HttpEntity<>(userRequest);
        ResponseEntity<SuperheroResponse> responseEntity = restTemplateBasicAuth()
                .postForEntity(url, requestSuperheroEntity , SuperheroResponse.class);

        assertThat(responseEntity.getStatusCode(), is(CREATED));
        assertThat(responseEntity.getHeaders().getContentType(), is(APPLICATION_JSON));
    }
}
