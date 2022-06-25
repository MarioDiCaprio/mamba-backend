package com.mariodicaprio.mamba.graphql.queries;


import com.mariodicaprio.mamba.entities.Post;
import com.mariodicaprio.mamba.services.PostService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@SuppressWarnings("unused")
public class PostQueryResolver implements GraphQLQueryResolver {

    @Autowired
    private PostService postService;

    //////////////////////////////////////////////////////////////////////////////////////

    public List<Post> postAll(int page) {
        return postService.postAll(page);
    }

    public Post postById(UUID postId) {
        return postService.postById(postId);
    }

    public List<Post> postsByTitle(String title) {
        return postService.postsByTitle(title);
    }

}
