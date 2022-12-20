package com.mariodicaprio.mamba.repositories;


import com.mariodicaprio.mamba.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    UserRepository userRepository;

    ///////////////////////////////////////////////////////////////////

    @Test
    void findByUsername() {
        // create user first
        User user = new User();
        user.setUsername("hello");
        userRepository.save(user);
        // test
        User tmp = userRepository.findByUsername("hello").orElseThrow();
        assertThat(user).isEqualTo(tmp);
    }

    @Test
    void existsByUsername() {
        // create user first
        User user = new User();
        user.setUsername("hello");
        userRepository.save(user);
        // test: existing username
        var exists = userRepository.existsByUsername("hello");
        assertThat(exists).isTrue();
        // test: username not existing
        exists = userRepository.existsByUsername("world");
        assertThat(exists).isFalse();
    }

}
