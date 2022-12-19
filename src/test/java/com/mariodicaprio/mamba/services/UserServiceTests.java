package com.mariodicaprio.mamba.services;


import com.mariodicaprio.mamba.entities.User;
import com.mariodicaprio.mamba.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
public class UserServiceTests {

    @Autowired
    UserRepository userRepository;

    ////////////////////////////////////////////////////////////////////

    @Test
    void findById() {
        // create user first
        User user = new User();
        userRepository.save(user);
        //test
        User tmp = userRepository.findById(user.getUserId()).orElseThrow();
        assertThat(user).isEqualTo(tmp);
    }

    @Test
    void findByUsername() {
        // create user first
        User user = new User();
        user.setUsername("hello");
        userRepository.save(user);
        //test
        User tmp = userRepository.findByUsername("hello").orElseThrow();
        assertThat(user).isEqualTo(tmp);
    }

}
