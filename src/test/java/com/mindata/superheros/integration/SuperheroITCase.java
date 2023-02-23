package com.mindata.superheros.integration;

import com.mindata.superheros.model.SuperheroResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static java.util.Objects.requireNonNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpStatus.OK;

public class SuperheroITCase extends IntegrationITCase{

    @Test
    public void getSuperheros() {
        ResponseEntity<SuperheroResponse[]> responseEntity = restTemplate.getForEntity(SUPERHERO_URL, SuperheroResponse[].class);

        assertThat(responseEntity.getStatusCode(), is(OK));
        assertThat((requireNonNull(responseEntity.getBody()).length), is(2));
    }

}
