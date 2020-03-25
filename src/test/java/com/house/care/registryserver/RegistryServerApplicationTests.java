package com.house.care.registryserver;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RegistryServerApplicationTests {

    @Test
    @DisplayName("Test Spring @Autowired Integration")
    void contextLoads() {

        assertTrue(true);
    }

}
