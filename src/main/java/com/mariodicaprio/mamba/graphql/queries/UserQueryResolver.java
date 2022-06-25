package com.mariodicaprio.mamba.graphql.queries;


import com.mariodicaprio.mamba.entities.Post;
import com.mariodicaprio.mamba.entities.User;
import com.mariodicaprio.mamba.repositories.PostRepository;
import com.mariodicaprio.mamba.repositories.UserRepository;
import com.mariodicaprio.mamba.services.UserService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@SuppressWarnings("unused")
public class UserQueryResolver implements GraphQLQueryResolver {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    ///////////////////////////////////////////////////////////////////////////////

    public List<User> userAll() {
        return userService.userAll();
    }

    public User userById(UUID userId) {
        return userService.userById(userId);
    }

    public User userByUsername(String username) {
        return userService.userByUsername(username);
    }

    public User userLikePost(UUID userId, UUID postId) {
        User user = userRepository.findById(userId).orElse(null);
        Post post = postRepository.findById(postId).orElse(null);
        if (user == null || post == null) {
            return null;
        }
        return userService.likePost(user, post);
    }

}
