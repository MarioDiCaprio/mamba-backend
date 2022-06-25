package com.mariodicaprio.mamba.responses;


import lombok.Data;

/**
 * This class represents the response body of the endpoint {@code /login}.
 * Each login attempt has the following properties:
 * <ul>
 *     <li>The username of the attempted login</li>
 *     <li>The password of the attempted login</li>
 *     <li>Whether the login attempt was valid</li>
 * </ul>
 */
@Data
public class LoginResponse {

    private final String username;
    private final String password;
    private final boolean valid;

}
