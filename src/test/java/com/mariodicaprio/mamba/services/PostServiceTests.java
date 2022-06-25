package com.mariodicaprio.mamba.services;


import com.mariodicaprio.mamba.entities.Post;
import com.mariodicaprio.mamba.repositories.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class PostServiceTests {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    ///////////////////////////////////////////////////////////////////////

    @Test
    void postAll() {
        // create 20 posts
        for (int i=0; i<20; i++) {
            Post post = new Post();
            post.setTitle("Post #" + i);
            postRepository.save(post);
        }

        // fetch first 15 posts (page 1)
        List<Post> posts = postService.postAll(1);

        // assert size = 15
        assertThat(posts.size()).isEqualTo(15);

        // assert posts are sorted by date created (descending)
        int j = 19;
        for (Post post : posts) {
            String title1 = post.getTitle();
            String title2 = "Post #" + j--;
            assertThat(title1).isEqualTo(title2);
        }
    }

}
