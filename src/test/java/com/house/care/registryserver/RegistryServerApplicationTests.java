package com.house.care.registryserver;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.Method;
import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.house.care.registryserver.config.OAuth2SsoConfiguration;

@AutoConfigureMockMvc
@ContextConfiguration
@AutoConfigureWebMvc
@SpringBootTest
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
    @WithMockUser(roles = "ADMIN")
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

    @Test
    @DisplayName("Test Auth")
    @WithMockUser(roles = "ADMIN")
    void testAuth() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/management/info")
                .with(testSecurityContext())
                .with(csrf())
                // .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andReturn();

        String resultCZ = result.getResponse().getContentAsString();
        assertNotNull(resultCZ);
    }

    @Test
    @DisplayName("Test userAuthoritiesMapper")
    void userAuthoritiesMapper() throws Exception {

        SimpleAuthorityMapper retorno = new SimpleAuthorityMapper();

        Collection<OidcUserAuthority> authorities = new HashSet<>();

        Map<String, Object> claims = new HashMap<>();
        claims.put("ADMIN", "ADMIN");

        OidcUserAuthority oidcUserAuthority = new OidcUserAuthority(new OidcIdToken("sgdfgdgfdgfdg", Instant.now(), Instant.now().plusSeconds(100000l), claims));
        authorities.add(oidcUserAuthority);

        Method method = OAuth2SsoConfiguration.class.getDeclaredMethod("userAuthoritiesMapper");
        method.setAccessible(true);
        method.invoke(retorno, authorities);
        assertNotNull(retorno);
    }

}
