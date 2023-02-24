package com.mindata.superheros.integration;

import com.mindata.superheros.model.SuperheroRequest;
import com.mindata.superheros.model.SuperheroResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.sql.Date;

import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.requireNonNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

public class SuperheroITCase extends IntegrationITCase{

    @Test
    public void getSuperheros() {
        ResponseEntity<SuperheroResponse[]> responseEntity = restTemplate.getForEntity(SUPERHERO_URL, SuperheroResponse[].class);

        assertThat(responseEntity.getStatusCode(), is(OK));
        assertThat(responseEntity.getHeaders().getContentType(), is(APPLICATION_JSON));
        assertThat((requireNonNull(responseEntity.getBody()).length), is(2));
    }

    @Test
    public void getSuperhero() {
        String URL = SUPERHERO_URL + "/1";
        ResponseEntity<SuperheroResponse> responseEntity = restTemplate.getForEntity(URL, SuperheroResponse.class);

        assertThat(responseEntity.getStatusCode(), is(OK));
        assertThat(responseEntity.getHeaders().getContentType(), is(APPLICATION_JSON));
        assertThat(requireNonNull(responseEntity.getBody()).getName(), is("Superman"));
    }

    @Test
    public void getSuperherosFilterByNameSubString() {
        String URL = SUPERHERO_URL + "?name=man";
        ResponseEntity<SuperheroResponse[]> responseEntity = restTemplate.getForEntity(URL, SuperheroResponse[].class);

        assertThat(responseEntity.getStatusCode(), is(OK));
        assertThat(responseEntity.getHeaders().getContentType(), is(APPLICATION_JSON));
        assertThat((requireNonNull(responseEntity.getBody()).length), is(2));
    }

    @Test
    public void getSuperherosFilterByOriginSubString() {
        String URL = SUPERHERO_URL + "?origin=" + encode("New York" , UTF_8) ;
        ResponseEntity<SuperheroResponse[]> responseEntity = restTemplate.getForEntity(URL, SuperheroResponse[].class);

        assertThat(responseEntity.getStatusCode(), is(OK));
        assertThat(responseEntity.getHeaders().getContentType(), is(APPLICATION_JSON));
        assertThat((requireNonNull(responseEntity.getBody()).length), is(1));
    }

    @Test
    public void postSuperhero() {
        SuperheroRequest superheroRequest = new SuperheroRequest(null, "Spiderman", "Male", "New York, US, Earth", Date.valueOf("1962-08-10"));

        HttpEntity<SuperheroRequest> requestSuperheroEntity = new HttpEntity<>(superheroRequest);
        ResponseEntity<SuperheroResponse> responseEntity = restTemplate.withBasicAuth("user", "password")
                .postForEntity(SUPERHERO_URL, requestSuperheroEntity , SuperheroResponse.class);

        assertThat(responseEntity.getStatusCode(), is(CREATED));
        assertThat(responseEntity.getHeaders().getContentType(), is(APPLICATION_JSON));
        assertThat(requireNonNull(responseEntity.getBody()).getName(), is("Spiderman"));
        assertThat(requireNonNull(responseEntity.getBody()).getId(), is(3));
    }

    @Test
    public void updateSuperhero() {
        SuperheroRequest superheroRequest = new SuperheroRequest(1, "Superman", "Male", "Buenos Aires, Argentina, Earth", Date.valueOf("1984-07-04"));
        HttpEntity<SuperheroRequest> requestSuperheroEntity = new HttpEntity<>(superheroRequest);

        ResponseEntity<SuperheroResponse> responseEntity = restTemplate.withBasicAuth("user", "password")
                .exchange(SUPERHERO_URL, PATCH, requestSuperheroEntity, SuperheroResponse.class);

        String URL = SUPERHERO_URL + "/1";
        ResponseEntity<SuperheroResponse> updatedResponseEntity = restTemplate.getForEntity(URL, SuperheroResponse.class);

        assertThat(responseEntity.getStatusCode(), is(ACCEPTED));
        assertThat(responseEntity.getHeaders().getContentType(), is(APPLICATION_JSON));
        assertThat(requireNonNull(responseEntity.getBody()).getName(), is("Superman"));
        assertThat(requireNonNull(responseEntity.getBody()).getOrigin(), is("Buenos Aires, Argentina, Earth"));

        assertThat(updatedResponseEntity.getStatusCode(), is(OK));
        assertThat(updatedResponseEntity.getHeaders().getContentType(), is(APPLICATION_JSON));
        assertThat(requireNonNull(updatedResponseEntity.getBody()).getName(), is("Superman"));
        assertThat(requireNonNull(updatedResponseEntity.getBody()).getOrigin(), is("Buenos Aires, Argentina, Earth"));
    }
}
