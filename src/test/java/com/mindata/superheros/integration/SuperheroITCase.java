package com.mindata.superheros.integration;

import com.mindata.superheros.model.request.SuperheroRequest;
import com.mindata.superheros.model.response.SuperheroResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.sql.Date;

import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.requireNonNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

public class SuperheroITCase extends IntegrationITCase {

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
        assertThat(requireNonNull(responseEntity.getBody()).name(), is("Superman"));
    }

    @Test
    public void getSuperheroByUnknownId() {
        String URL = SUPERHERO_URL + "/10000";
        ResponseEntity<SuperheroResponse> responseEntity = restTemplate.getForEntity(URL, SuperheroResponse.class);

        assertThat(responseEntity.getStatusCode(), is(NOT_FOUND));
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
        ResponseEntity<SuperheroResponse> responseEntity = restTemplateBasicAuth()
                .postForEntity(SUPERHERO_URL, requestSuperheroEntity , SuperheroResponse.class);

        assertThat(responseEntity.getStatusCode(), is(CREATED));
        assertThat(responseEntity.getHeaders().getContentType(), is(APPLICATION_JSON));
        assertThat(requireNonNull(responseEntity.getBody()).name(), is("Spiderman"));
        assertThat(requireNonNull(responseEntity.getBody()).id(), is(3));
    }

    @Test
    public void updateSuperhero() {
        String URL = SUPERHERO_URL + "/1";
        SuperheroRequest superheroRequest = new SuperheroRequest(1, "Superman", "Male", "Buenos Aires, Argentina, Earth", Date.valueOf("1984-07-04"));
        HttpEntity<SuperheroRequest> requestSuperheroEntity = new HttpEntity<>(superheroRequest);

        ResponseEntity<SuperheroResponse> responseEntity = restTemplateBasicAuth()
                .exchange(URL, PATCH, requestSuperheroEntity, SuperheroResponse.class);

        assertThat(responseEntity.getStatusCode(), is(ACCEPTED));
        assertThat(responseEntity.getHeaders().getContentType(), is(APPLICATION_JSON));
        assertThat(requireNonNull(responseEntity.getBody()).name(), is("Superman"));
        assertThat(requireNonNull(responseEntity.getBody()).origin(), is("Buenos Aires, Argentina, Earth"));
    }

    @Test
    public void partiallyUpdateSuperhero() {
        String URL = SUPERHERO_URL + "/1";
        String patchJsonBody = "[{\"op\": \"replace\", \"path\": \"/origin\", \"value\": \"Buenos Aires, Argentina, Earth\"}]";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json-patch+json"));
        HttpEntity<String> entity = new HttpEntity<>(patchJsonBody, headers);

        ResponseEntity<SuperheroResponse> responseEntity = restTemplateBasicAuth().exchange(URL, PATCH, entity, SuperheroResponse.class);

        assertThat(responseEntity.getStatusCode(), is(ACCEPTED));
        assertThat(responseEntity.getHeaders().getContentType(), is(APPLICATION_JSON));
        assertThat(requireNonNull(responseEntity.getBody()).name(), is("Superman"));
        assertThat(requireNonNull(responseEntity.getBody()).gender(), is("Male"));
        assertThat(requireNonNull(responseEntity.getBody()).origin(), is("Buenos Aires, Argentina, Earth"));
    }

    @Test
    public void partiallyUpdateSuperheroByUnknownId() {
        String URL = SUPERHERO_URL + "/1000";
        String patchJsonBody = "[{\"op\": \"replace\", \"path\": \"/origin\", \"value\": \"Buenos Aires, Argentina, Earth\"}]";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json-patch+json"));
        HttpEntity<String> entity = new HttpEntity<>(patchJsonBody, headers);

        ResponseEntity<SuperheroResponse> responseEntity = restTemplateBasicAuth().exchange(URL, PATCH, entity, SuperheroResponse.class);

        assertThat(responseEntity.getStatusCode(), is(NOT_FOUND));
        assertThat(responseEntity.getHeaders().getContentType(), is(APPLICATION_JSON));
    }

    @Test
    public void updateSuperheroWithIdOnBodyDifferentFromIdOnUrl() {
        String URL = SUPERHERO_URL + "/1";
        SuperheroRequest superheroRequest = new SuperheroRequest(2, "Superman", "Male", "Buenos Aires, Argentina, Earth", Date.valueOf("1984-07-04"));
        HttpEntity<SuperheroRequest> requestSuperheroEntity = new HttpEntity<>(superheroRequest);

        ResponseEntity<SuperheroResponse> responseEntity = restTemplateBasicAuth()
                .exchange(URL, PATCH, requestSuperheroEntity, SuperheroResponse.class);

        assertThat(responseEntity.getStatusCode(), is(BAD_REQUEST));
    }

    @Test
    public void updateSuperheroWithMalformedJsonPath() {
        String URL = SUPERHERO_URL + "/1";
        String patchJsonBody = "[{\"op\": \"replace\", \"path\": \"/iDontExist\", \"value\": \"Buenos Aires, Argentina, Earth\"}]";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json-patch+json"));
        HttpEntity<String> entity = new HttpEntity<>(patchJsonBody, headers);

        ResponseEntity<String> responseEntity = restTemplateBasicAuth().exchange(URL, PATCH, entity, String.class);

        assertThat(responseEntity.getStatusCode(), is(BAD_REQUEST));
        assertThat(responseEntity.getHeaders().getContentType(), is(APPLICATION_JSON));
    }

    @Test
    public void deleteSuperhero() {
        String URL = SUPERHERO_URL + "/1";
        ResponseEntity<Void> responseEntity = restTemplateBasicAuth().exchange(URL, DELETE, null, Void.class);

        assertThat(responseEntity.getStatusCode(), is(NO_CONTENT));
        assertThat(responseEntity.getHeaders().getContentType(), is(APPLICATION_JSON));
    }

    @Test
    public void deleteUSuperheroByUnknownId() {
        String URL = SUPERHERO_URL + "/10000";
        ResponseEntity<Void> responseEntity = restTemplateBasicAuth().exchange(URL, DELETE, null, Void.class);

        assertThat(responseEntity.getStatusCode(), is(NOT_FOUND));
        assertThat(responseEntity.getHeaders().getContentType(), is(APPLICATION_JSON));
    }
}
