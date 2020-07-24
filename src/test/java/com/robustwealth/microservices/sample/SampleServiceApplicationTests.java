package com.robustwealth.microservices.sample;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SampleServiceApplicationTests {

    @Test
    void contextLoads() {
        // As long as the context loads, then this test passes
        assertTrue(true);
    }

}
