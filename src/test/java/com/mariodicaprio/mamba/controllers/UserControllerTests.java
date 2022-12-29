package com.mariodicaprio.mamba.controllers;


import com.mariodicaprio.mamba.entities.User;
import com.mariodicaprio.mamba.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MockMvc mockMvc;

    /////////////////////////////////////////////////////////////

    @Test
    void byId() throws Exception {
        // create user first
        User user = new User();
        userRepository.save(user);
        // test
        String url = "/user/byId?userId=" + user.getUserId();
        mockMvc
                .perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(user.getUserId().toString()));
    }

    @Test
    void byUsername() throws Exception {
        // create user first
        User user = new User();
        user.setUsername("hello");
        userRepository.save(user);
        // test
        String url = "/user/byUsername?username=" + user.getUsername();
        mockMvc.
                perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(user.getUsername()));
    }

}
