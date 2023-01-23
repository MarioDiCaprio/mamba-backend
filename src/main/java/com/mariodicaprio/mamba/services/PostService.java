package com.mariodicaprio.mamba.services;


import com.mariodicaprio.mamba.entities.Post;
import com.mariodicaprio.mamba.exceptions.UserNotFoundException;
import com.mariodicaprio.mamba.requests.PostCreationRequest;
import org.springframework.data.domain.Page;


public interface PostService {

    Page<Post> all(int page);

    void createPost(PostCreationRequest request) throws UserNotFoundException;

}
