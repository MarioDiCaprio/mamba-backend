package com.mariodicaprio.mamba.graphql.mutations;


import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@Transactional
public class PostMutationResolverTests {

    @Autowired
    GraphQLTestTemplate testTemplate;

    /////////////////////////////////////////////////////////////////////

    @Test
    void createPost() throws IOException {
        String path = "graphql/mutations/createPost.graphql";

        GraphQLResponse response = testTemplate.postForResource(path);
        assertThat(response.isOk()).isTrue();
        assertThat(response.get("$.data.createPost.title")).isNotNull();
    }

}
