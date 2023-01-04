package com.mariodicaprio.mamba.services;


import com.mariodicaprio.mamba.exceptions.InvalidLoginException;
import org.springframework.security.core.Authentication;

public interface LoginService {

    boolean isLoginValid(Authentication authentication);

    String login(Authentication authentication) throws InvalidLoginException;

}
