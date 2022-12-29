package com.mariodicaprio.mamba.services;


import com.mariodicaprio.mamba.entities.User;
import com.mariodicaprio.mamba.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@Transactional
public class LoginServiceTests {

    @Autowired
    LoginService loginService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    ////////////////////////////////////////////////////////

    @Test
    void isLoginValid() {
        // create user first
        var user = new User();
        user.setUsername("hello");
        user.setPassword(passwordEncoder.encode("world"));
        userRepository.save(user);
        // test: wrong password
        var valid = loginService.isLoginValid("hello", "not world");
        assertThat(valid).isFalse();
        // test: correct password
        valid = loginService.isLoginValid("hello", "world");
        assertThat(valid).isTrue();
    }

}
