package com.mariodicaprio.mamba.requests;


import lombok.Data;


@Data
public class PostCreationRequest {

    private final String title;

    private final String description;

    private final PostCreationRequestMedia media;

    private final String username;


    @Data
    public static class PostCreationRequestMedia {

        private final byte[] data;

        private final String type;

    }

}
