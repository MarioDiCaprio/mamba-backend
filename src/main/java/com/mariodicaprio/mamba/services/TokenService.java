package com.mariodicaprio.mamba.services;


import org.springframework.security.core.Authentication;


public interface TokenService {

    String generateToken(Authentication authentication);

}
