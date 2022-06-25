package com.mariodicaprio.mamba.graphql.mutations;


import com.mariodicaprio.mamba.entities.Post;
import com.mariodicaprio.mamba.entities.User;
import com.mariodicaprio.mamba.repositories.PostRepository;
import com.mariodicaprio.mamba.repositories.UserRepository;
import com.mariodicaprio.mamba.services.UserService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@SuppressWarnings("unused")
public class UserMutationResolver implements GraphQLMutationResolver {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    ////////////////////////////////////////////////////////////////////////////

    public User userLikePost(UUID userId, UUID postId) {
        User user = userRepository.findById(userId).orElse(null);
        Post post = postRepository.findById(postId).orElse(null);
        if (user == null || post == null) {
            return null;
        }
        return userService.likePost(user, post);
    }

}
