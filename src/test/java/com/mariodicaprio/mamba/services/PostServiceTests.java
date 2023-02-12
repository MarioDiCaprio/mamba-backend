package com.mariodicaprio.mamba.services;

import com.mariodicaprio.mamba.entities.Post;
import com.mariodicaprio.mamba.entities.User;
import com.mariodicaprio.mamba.repositories.PostRepository;
import com.mariodicaprio.mamba.repositories.UserRepository;
import com.mariodicaprio.mamba.requests.PostCreationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    void all() {
        // generate 20 posts
        List<Post> posts = new ArrayList<>();
        for (int i=0; i<20; i++) {
            Post post = new Post();
            post.setDateCreated(new Date());
            posts.add(post);
            postRepository.save(post);
        }
        // sort by newest first
        System.out.println(posts.get(0).getDateCreated());
        posts.sort((a, b) -> b.getDateCreated().compareTo(a.getDateCreated()));
        // fetch first page and assert only 15 posts are contained
        var page1 = postService.all(1).stream().toList();
        assertThat(page1.size()).isEqualTo(15);
        for (int i=0; i<page1.size(); i++) {
            var x = page1.get(i);
            var y = posts.get(i);
            assertThat(x).isEqualTo(y);
        }
        // fetch second page and assert only 5 posts are contained
        var page2 = postService.all(2).stream().toList();
        assertThat(page2.size()).isEqualTo(5);
        for (int i=0; i<page2.size(); i++) {
            var x = page2.get(i);
            var y = posts.get(15 + i);
            assertThat(x).isEqualTo(y);
        }
    }

    @Test
    void createPost() throws Exception {
        // create user first
        var user = new User();
        userRepository.save(user);
        // make request
        var request = new PostCreationRequest(
                "Hello",
                "Hello, World!",
                new PostCreationRequest.PostCreationRequestMedia(
                        new byte[] {1, 2, 3, 4, 5},
                        "media/testingType"
                ),
                user.getUserId()
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
