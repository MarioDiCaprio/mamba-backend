package com.mariodicaprio.mamba.services;


import com.mariodicaprio.mamba.entities.Post;
import com.mariodicaprio.mamba.entities.User;
import com.mariodicaprio.mamba.repositories.PostRepository;
import com.mariodicaprio.mamba.repositories.UserRepository;
import com.mariodicaprio.mamba.requests.LikeRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class UserServiceTests {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    ////////////////////////////////////////////////////////////////////

    @Test
    void findById() throws Exception {
        // create user first
        User user = new User();
        userRepository.save(user);
        //test
        User tmp = userService.findById(user.getUserId());
        assertThat(user).isEqualTo(tmp);
    }

    @Test
    void findByUsername() throws Exception {
        // create user first
        User user = new User();
        user.setUsername("hello");
        userRepository.save(user);
        //test
        User tmp = userService.findByUsername("hello");
        assertThat(user).isEqualTo(tmp);
    }

    @Test
    void likePost() {
        // create entities first
        User user = new User();
        Post post = new Post();
        userRepository.save(user);
        postRepository.save(post);
        // make request
        LikeRequest request = new LikeRequest(user.getUserId(), post.getPostId(), true);
        userService.likePost(request);
        // test
        assertThat(user.getLikes().contains(post)).isTrue();
        assertThat(post.getLikes().contains(user)).isTrue();
        // unlike
        request = new LikeRequest(user.getUserId(), post.getPostId(), false);
        userService.likePost(request);
        // test
        assertThat(user.getLikes().contains(post)).isFalse();
        assertThat(post.getLikes().contains(user)).isFalse();
    }

}
