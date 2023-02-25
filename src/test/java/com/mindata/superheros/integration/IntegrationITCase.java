package com.mindata.superheros.integration;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Sql(scripts = "/sql/testData.sql", executionPhase = BEFORE_TEST_METHOD)
public abstract class IntegrationITCase {

    @Autowired
    protected TestRestTemplate restTemplate;

    @LocalServerPort
    protected int port;

    protected String HOST;
    protected String SUPERHERO_URL;
    protected String USER_URL;

    @BeforeEach
    public void setUp() {
        HOST = "http://localhost:" + port;
        SUPERHERO_URL = HOST + "/api/superheros";
        USER_URL = HOST + "/api/users";

        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    protected TestRestTemplate restTemplateBasicAuth() {
        return restTemplate.withBasicAuth("user", "password");
    }

}
