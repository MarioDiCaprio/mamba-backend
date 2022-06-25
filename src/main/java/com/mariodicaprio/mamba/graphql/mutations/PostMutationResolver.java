package com.mariodicaprio.mamba.graphql.mutations;


import com.mariodicaprio.mamba.entities.Post;
import com.mariodicaprio.mamba.requests.CreatePostRequest;
import com.mariodicaprio.mamba.services.PostService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@SuppressWarnings("unused")
public class PostMutationResolver implements GraphQLMutationResolver {

    @Autowired
    private PostService postService;

    /////////////////////////////////////////////////////////////////////////////

    public Post createPost(CreatePostRequest request) {
        return postService.createPost(request);
    }

}
