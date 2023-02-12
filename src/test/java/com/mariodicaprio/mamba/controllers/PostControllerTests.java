package com.mariodicaprio.mamba.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mariodicaprio.mamba.entities.Post;
import com.mariodicaprio.mamba.entities.User;
import com.mariodicaprio.mamba.repositories.PostRepository;
import com.mariodicaprio.mamba.repositories.UserRepository;
import com.mariodicaprio.mamba.requests.PostCreationRequest;
import com.mariodicaprio.mamba.requests.SignupRequest;
import com.mariodicaprio.mamba.services.SignupService;
import com.mariodicaprio.mamba.utils.SecurityTools;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PostControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SignupService signupService;

    @Autowired
    SecurityTools securityTools;

    ////////////////////////////////////////////////////////////

    @Test
    void all() throws Exception {
        // create user first
        var user = new User();
        userRepository.save(user);
        // create 20 posts
        for (int i=0; i<20; i++) {
            var post = new Post();
            post.setOwner(user);
            postRepository.save(post);
        }
        var url = "/post/all?page=1";
        // send request
        mockMvc
                .perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size").value(15));
    }

    @Test
    @WithMockUser
    void create() throws Exception {
        // register user first
        signupService.signup(new SignupRequest("user", "helloworld@gmail.com", "password"));
        var user = userRepository.findByUsername("user").orElseThrow();
        // login first
        var token = securityTools.loginAndGetToken("user", "password", mockMvc);
        // instantiate request
        var request = new PostCreationRequest(
                "Hello",
                "Hello, World!",
                new PostCreationRequest.PostCreationRequestMedia(
                        new byte[] {1, 2, 3, 4, 5},
                        "media/testingType"
                ),
                user.getUsername()
        );
        var json = objectMapper.writeValueAsString(request);
        // send request
        var url = "/post/create";
        mockMvc
                .perform(
                        post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Bearer", token)
                )
                .andExpect(status().isOk());
        // make sure post was created
        var post = postRepository.findByTitle("Hello").get(0);
        assertThat(post.getTitle()).isEqualTo("Hello");
        assertThat(post.getDescription()).isEqualTo("Hello, World!");
        assertThat(post.getMedia().getData()).isEqualTo(new byte[] {1, 2, 3, 4, 5});
        assertThat(post.getMedia().getType()).isEqualTo("media/testingType");
        assertThat(post.getMedia().getMediaId()).isNotNull();
        assertThat(post.getOwner()).isEqualTo(user);
    }

}
