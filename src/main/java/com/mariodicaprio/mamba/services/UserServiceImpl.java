package com.mariodicaprio.mamba.services;


import com.mariodicaprio.mamba.entities.Post;
import com.mariodicaprio.mamba.entities.User;
import com.mariodicaprio.mamba.exceptions.UserNotFoundException;
import com.mariodicaprio.mamba.repositories.PostRepository;
import com.mariodicaprio.mamba.repositories.UserRepository;
import com.mariodicaprio.mamba.requests.LikeRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    //////////////////////////////////////////////////////////////////

    @Override
    public User findById(UUID userId) throws UserNotFoundException {
        log.info("fetching user by ID: " + userId);
        var user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            log.warn("user with ID " + userId + " not found");
            throw new UserNotFoundException();
        }
        log.info("user with ID " + userId + " found");
        return user;
    }

    @Override
    public User findByUsername(String username) throws UserNotFoundException {
        log.info("fetching user by username: \"" + username + "\"");
        var user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            log.warn("user with username \"" + username + "\" not found");
            throw new UserNotFoundException();
        }
        log.info("user with username \"" + username + "\" found");
        return user;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void likePost(LikeRequest request) {
        log.info("Initiating like request");
        User user = userRepository.findById(request.getUserId()).orElse(null);
        if (user == null) return;

        Post post = postRepository.findById(request.getPostId()).orElse(null);
        if (post == null) return;

        boolean userLikesPost = user.getLikes().contains(post);
        if (userLikesPost && !request.isLike()) {
            // if user already likes post and wants to unlike
            user.getLikes().remove(post);
            post.getLikes().remove(user);
            userRepository.save(user);
        } else if (!userLikesPost && request.isLike()) {
            // if user does not like post yet and wants to like
            user.getLikes().add(post);
            post.getLikes().add(user);
            userRepository.save(user);
        }
    }

}
