package com.mariodicaprio.mamba.requests;


import com.mariodicaprio.mamba.entities.Post;
import lombok.Data;

import java.util.List;
import java.util.UUID;


@Data
public class CreatePostRequest {

    private final String title;
    private final String text;
    private final byte[] media;
    private final UUID ownerId;
    private final UUID repostsId;
    private final List<String> tagNames;
    private final Post.PostType type;

}
