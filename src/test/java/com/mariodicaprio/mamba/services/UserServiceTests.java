package com.mariodicaprio.mamba.services;


import com.mariodicaprio.mamba.entities.User;
import com.mariodicaprio.mamba.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class UserServiceTests {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    //////////////////////////////////////////////////////////////////////////////////////

    @Test
    void userAll() {
        // create 5 users
        for (int i=0; i<5; i++) {
            User tmp = new User();
            userRepository.save(tmp);
        }

        // assert all users can be fetched
        int numberOfUsers = userService.userAll().size();
        assertThat(numberOfUsers).isEqualTo(5);
    }

    @Test
    void userById() {
        // create user first
        User user = new User();
        userRepository.save(user);

        // assert user can be found
        User tmp = userRepository.findById(user.getUserId()).orElse(null);
        assertThat(tmp).isNotNull();
    }

    @Test
    void userByUsername() {
        // create user first
        User user = new User();
        user.setUsername("Hello");
        userRepository.save(user);

        // assert user can be found
        User tmp = userRepository.findByUsername(user.getUsername()).orElse(null);
        assertThat(tmp).isNotNull();
    }

}
