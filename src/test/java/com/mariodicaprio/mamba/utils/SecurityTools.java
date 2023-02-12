package com.mariodicaprio.mamba.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@Component
public class SecurityTools {

    @Autowired
    ObjectMapper objectMapper;

    /////////////////////////////////////////////////////////////////

    public String loginAndGetToken(String username, String password, MockMvc mockMvc) throws Exception {
        // encode username and password
        var auth = Base64.getEncoder().encodeToString((username + ':' + password).getBytes());
        var json = mockMvc
                .perform(post("/login").header("Authorization", auth))
                .andReturn()
                .getResponse()
                .getContentAsString();
        return JsonPath.read(json, "$.token");
    }

}
