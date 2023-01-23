package com.mariodicaprio.mamba.responses;


import com.mariodicaprio.mamba.entities.Post;
import com.mariodicaprio.mamba.entities.User;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Data
public class PostResponse {

    private final UUID postId;

    private final String title;

    private final String description;

    private final Date dateCreated;

    private final Date dateUpdated;

    private final MediaResponse media;

    private final UUID owner;

    private final List<UUID> likes;


    public PostResponse(Post post) {
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.description = post.getDescription();
        this.media = (post.getMedia() == null)? null : new MediaResponse(post.getMedia());
        this.dateCreated = post.getDateCreated();
        this.dateUpdated = post.getDateUpdated();
        this.owner = post.getOwner().getUserId();
        this.likes = post.getLikes().stream().map(User::getUserId).collect(Collectors.toList());
    }

}