package com.mariodicaprio.mamba.services;


import com.mariodicaprio.mamba.entities.User;
import com.mariodicaprio.mamba.exceptions.UserNotFoundException;

import java.util.UUID;


public interface UserService {

    User findById(UUID userId) throws UserNotFoundException;

    User findByUsername(String username) throws UserNotFoundException;

}
