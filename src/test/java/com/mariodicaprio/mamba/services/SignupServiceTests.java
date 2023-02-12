package com.mariodicaprio.mamba.services;


import com.mariodicaprio.mamba.exceptions.InvalidSignupException;
import com.mariodicaprio.mamba.repositories.UserRepository;
import com.mariodicaprio.mamba.requests.SignupRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class SignupServiceTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SignupService signupService;

    @Autowired
    PasswordEncoder passwordEncoder;

    ////////////////////////////////////////////////////////////////////////////

    @Test
    void signup() throws Exception {
        var request = new SignupRequest("hello", "helloworld@gmail.com", "world");
        // signup user
        signupService.signup(request);
        // make sure user was created with correct data
        var user = userRepository.findByUsername("hello").orElseThrow();
        assertThat(user.getUsername()).isEqualTo("hello");
        assertThat(user.getEmail()).isEqualTo("helloworld@gmail.com");
        assertThat(passwordEncoder.matches("world", user.getPassword())).isTrue();
        // make sure user cannot be signed up again
        assertThrows(InvalidSignupException.class, () -> signupService.signup(request));
    }

}
