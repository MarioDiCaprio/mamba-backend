package com.mariodicaprio.mamba.requests;


import lombok.Data;

import java.util.UUID;


@Data
public class LikeRequest {

    private final UUID userId;

    private final UUID postId;

    private final boolean like;

}
