package com.mariodicaprio.mamba.responses;


import com.mariodicaprio.mamba.entities.User;
import lombok.Data;

import java.util.UUID;


@Data
public class UserResponse {

    private final UUID userId;
    private final String username;
    private final String email;
    private final String password;

    public UserResponse(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

}
