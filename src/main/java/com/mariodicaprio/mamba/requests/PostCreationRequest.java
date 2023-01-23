package com.mariodicaprio.mamba.requests;


import lombok.Data;

import java.util.UUID;


@Data
public class PostCreationRequest {

    private final String title;

    private final String description;

    private final PostCreationRequestMedia media;

    private final UUID ownerId;


    @Data
    public static class PostCreationRequestMedia {

        private final byte[] data;

        private final String type;

    }

}
