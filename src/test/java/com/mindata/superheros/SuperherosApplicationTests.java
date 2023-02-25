package com.mindata.superheros;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.slf4j.LoggerFactory.getLogger;

@SpringBootTest
class SuperherosApplicationTests {

    Logger logger = getLogger(SuperherosApplicationTests.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
        logger.info("The password is: {}", passwordEncoder.encode("password"));
    }

}
