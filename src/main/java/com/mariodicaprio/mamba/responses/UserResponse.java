package com.mariodicaprio.mamba.responses;


import com.mariodicaprio.mamba.entities.Post;
import com.mariodicaprio.mamba.entities.User;
import lombok.Data;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Data
public class UserResponse {

    private final UUID userId;
    private final String username;
    private final String email;
    private final String password;

    private final List<UUID> followers;

    private final List<UUID> following;

    private final List<UUID> posts;

    private final List<UUID> likes;

    public UserResponse(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.followers = user.getFollowers().stream().map(User::getUserId).collect(Collectors.toList());
        this.following = user.getFollowers().stream().map(User::getUserId).collect(Collectors.toList());
        this.posts = user.getPosts().stream().map(Post::getPostId).collect(Collectors.toList());
        this.likes = user.getLikes().stream().map(Post::getPostId).collect(Collectors.toList());
    }

}
