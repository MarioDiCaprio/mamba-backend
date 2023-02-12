package com.mariodicaprio.mamba.controllers;


import com.mariodicaprio.mamba.exceptions.InvalidSignupException;
import com.mariodicaprio.mamba.requests.SignupRequest;
import com.mariodicaprio.mamba.services.SignupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LoginControllerTests {

    @Autowired
    SignupService signupService;

    @Autowired
    MockMvc mockMvc;

    ///////////////////////////////////////////////////////////////

    @BeforeEach
    void createUser() throws InvalidSignupException {
        signupService.signup(new SignupRequest("hello", "helloworld@gmail.com", "world"));
    }

    ///////////////////////////////////////////////////////////////

    @Test
    void shouldRejectInvalidLogin() throws Exception {
        mockMvc
                .perform(post("/login").with(httpBasic("hello", "xyz")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldAcceptValidLoginAndGenerateJWT() throws Exception {
        String jwt = mockMvc
                .perform(post("/login").with(httpBasic("hello", "world")))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(jwt).isNotNull();
    }

}
