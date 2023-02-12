package com.mariodicaprio.mamba.services;

import com.mariodicaprio.mamba.entities.User;
import com.mariodicaprio.mamba.repositories.PostRepository;
import com.mariodicaprio.mamba.repositories.UserRepository;
import com.mariodicaprio.mamba.requests.PostCreationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class PostServiceTests {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    //////////////////////////////////////////////////////

    @Test
    void createPost() throws Exception {
        // create user first
        var user = new User();
        user.setUsername("user");
        userRepository.save(user);
        // make request
        var request = new PostCreationRequest(
                "Hello",
                "Hello, World!",
                new PostCreationRequest.PostCreationRequestMedia(
                        new byte[] {1, 2, 3, 4, 5},
                        "media/testingType"
                ),
                user.getUsername()
        );
        postService.createPost(request);
        // get post
        var post = postRepository.findByTitle("Hello").get(0);
        assertThat(post.getTitle()).isEqualTo("Hello");
        assertThat(post.getDescription()).isEqualTo("Hello, World!");
        assertThat(post.getMedia().getData()).isEqualTo(new byte[] {1, 2, 3, 4, 5});
        assertThat(post.getMedia().getType()).isEqualTo("media/testingType");
        assertThat(post.getMedia().getMediaId()).isNotNull();
        assertThat(post.getOwner()).isEqualTo(user);
    }

}
