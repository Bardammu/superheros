package com.mindata.superheros.integration;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class SecurityITCase extends IntegrationITCase {
    
    @Test
    public void performAnonymousRequest() {
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(SUPERHERO_URL, Object.class);

        assertThat(responseEntity.getStatusCode(), is(OK));
    }

    @Test
    public void performUnauthorizedRequest() {
        HttpEntity<String> requestSuperheroEntity = new HttpEntity<>("Simple test");
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(SUPERHERO_URL, requestSuperheroEntity , String.class);

        assertThat(responseEntity.getStatusCode(), is(UNAUTHORIZED));
    }

    @Test
    public void performAuthorizedRequest() {
        HttpEntity<String> requestSuperheroEntity = new HttpEntity<>("Simple test");
        ResponseEntity<String> responseEntity = restTemplate.withBasicAuth("user", "password")
                .postForEntity(SUPERHERO_URL, requestSuperheroEntity , String.class);

        assertThat(responseEntity.getStatusCode(), is(OK));
    }

}
