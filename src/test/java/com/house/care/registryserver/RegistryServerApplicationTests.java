package com.house.care.registryserver;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class RegistryServerApplicationTests {

    private final static String TEST_USER_ID = "user-id-123";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test Spring @Autowired Integration")
    void contextLoads() {

        assertTrue(true);
    }

    @Test
    @DisplayName("Test Spring Health")
    void testHealth() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/management/health")
                .with(user(TEST_USER_ID))
                .with(csrf())
                // .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andReturn();
        String resultCZ = result.getResponse().getContentAsString();
        assertNotNull(resultCZ);
    }

    @Test
    @DisplayName("Test Spring Info")
    // @WithMockUser(roles = "ADMIN")
    void testEureka() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/management/info")
                .with(user(TEST_USER_ID).roles("ADMIN"))
                .with(csrf())
                // .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andReturn();
        String resultCZ = result.getResponse().getContentAsString();
        assertNotNull(resultCZ);
    }

}
