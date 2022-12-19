package com.mariodicaprio.mamba.services;


import com.mariodicaprio.mamba.entities.User;
import com.mariodicaprio.mamba.exceptions.UserNotFoundException;
import com.mariodicaprio.mamba.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    //////////////////////////////////////////////////////////////////

    @Override
    public User findById(UUID userId) throws UserNotFoundException {
        log.info("fetching user by ID: " + userId);
        var user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            log.trace("user with ID " + userId + " not found");
            throw new UserNotFoundException();
        }
        log.info("user with ID " + userId + " found");
        return user;
    }

    @Override
    public User findByUsername(String username) throws UserNotFoundException {
        log.info("fetching user by username: " + username);
        var user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            log.trace("user with username " + username + " not found");
            throw new UserNotFoundException();
        }
        log.info("user with username " + username + " found");
        return user;
    }

}
