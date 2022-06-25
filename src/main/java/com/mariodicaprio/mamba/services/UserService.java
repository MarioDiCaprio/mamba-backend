package com.mariodicaprio.mamba.services;


import com.mariodicaprio.mamba.entities.Post;
import com.mariodicaprio.mamba.entities.User;
import com.mariodicaprio.mamba.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /////////////////////////////////////////////////////////////////////////////

    @Transactional(readOnly = true)
    public List<User> userAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User userById(UUID userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Transactional(readOnly = true)
    public User userByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public User likePost(User user, Post post) {
        if (user.getLikes().contains(post)) {
            user.getLikes().remove(post);
            post.getLikes().remove(user);
        } else {
            user.getLikes().add(post);
            post.getLikes().add(user);
        }
        userRepository.save(user);
        return user;
    }

}
