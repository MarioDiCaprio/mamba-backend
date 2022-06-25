package com.mariodicaprio.mamba.graphql.mutations;


import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import com.mariodicaprio.mamba.entities.User;
import com.mariodicaprio.mamba.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@Transactional
public class LoginMutationResolverTests {

    @Autowired
    GraphQLTestTemplate testTemplate;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    ////////////////////////////////////////////////////////////////////////////////////

    // TODO make this test work!
    //@Test
    void login() throws IOException {
        String path = "graphql/mutations/login.graphql";

        // send request and ensure it is invalid, because user does not exist
        GraphQLResponse response = testTemplate.postForResource(path);
        assertThat(response.isOk()).isTrue();
        assertThat(response.get("$.data.login.valid")).isEqualTo("false");

        // create user
        User user = new User("Hello", "helloworld@gmail.com", passwordEncoder.encode("World"));
        userRepository.save(user);

        // assert login now successful
        response = testTemplate.postForResource(path);
        assertThat(response.isOk()).isTrue();
        assertThat(response.get("$.data.login.valid")).isEqualTo("true");
    }

}
