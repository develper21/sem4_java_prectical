package com.example.crud;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Application Context Load Test
 */
@SpringBootTest
@ActiveProfiles("test")
class CrudApplicationTests {
    
    @Test
    void contextLoads() {
        // Verifies that the Spring context loads successfully
    }
}
