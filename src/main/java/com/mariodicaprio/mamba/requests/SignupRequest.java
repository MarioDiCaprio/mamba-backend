package com.mariodicaprio.mamba.requests;


import com.sun.istack.NotNull;
import lombok.Data;


@Data
public class SignupRequest {

    @NotNull
    private final String username;

    @NotNull
    private final String email;

    @NotNull
    private final String password;

}
