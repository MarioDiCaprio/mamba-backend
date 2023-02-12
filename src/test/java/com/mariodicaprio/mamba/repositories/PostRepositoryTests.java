package com.mariodicaprio.mamba.repositories;


import com.mariodicaprio.mamba.entities.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@ActiveProfiles("test")
public class PostRepositoryTests {

    @Autowired
    PostRepository postRepository;

    ////////////////////////////////////////////////////////

    @Test
    void findByTitle() {
        // create 15 posts
        List<Post> posts = new ArrayList<>();
        for (int i=0; i<15; i++) {
            Post post = new Post();
            post.setTitle("Hello");
            postRepository.save(post);
            posts.add(post);
        }
        // create 10 unrelated posts
        for (int i=0; i<15; i++) {
            Post post = new Post();
            post.setTitle("World");
            postRepository.save(post);
        }
        // test
        List<Post> tmp = postRepository.findByTitle("Hello");
        assertThat(tmp.size()).isEqualTo(15);
        tmp.forEach(post -> assertThat(posts.contains(post)).isTrue());
    }

}
