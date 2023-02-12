package com.mariodicaprio.mamba.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mariodicaprio.mamba.repositories.UserRepository;
import com.mariodicaprio.mamba.requests.SignupRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SignupControllerTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    /////////////////////////////////////////////////////////////

    @Test
    void signup() throws Exception {
        var request = new SignupRequest("hello", "helloworld@gmail.com", "world");
        var json = objectMapper.writeValueAsString(request);
        // send request
        mockMvc
                .perform(post("/signup").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
        // make sure user was created with correct data
        var user = userRepository.findByUsername("hello").orElseThrow();
        assertThat(user.getUsername()).isEqualTo("hello");
        assertThat(user.getEmail()).isEqualTo("helloworld@gmail.com");
        assertThat(passwordEncoder.matches("world", user.getPassword())).isTrue();
        // make sure user cannot be signed up again
        mockMvc
                .perform(post("/signup").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isConflict());
    }

}
