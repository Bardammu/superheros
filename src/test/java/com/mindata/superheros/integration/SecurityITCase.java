package com.mindata.superheros.integration;

import com.mindata.superheros.model.request.SuperheroRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.sql.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpStatus.CREATED;
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
        SuperheroRequest superheroRequest = new SuperheroRequest(null, "Spiderman", "Male", "New York, US, Earth", Date.valueOf("1962-08-10"));
        HttpEntity<SuperheroRequest> requestSuperheroEntity = new HttpEntity<>(superheroRequest);
        ResponseEntity<String> responseEntity = restTemplateBasicAuth()
                .postForEntity(SUPERHERO_URL, requestSuperheroEntity , String.class);

        assertThat(responseEntity.getStatusCode(), is(CREATED));
    }

}
