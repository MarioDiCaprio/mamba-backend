package com.mariodicaprio.mamba.services;


import com.mariodicaprio.mamba.exceptions.InvalidSignupException;
import com.mariodicaprio.mamba.requests.SignupRequest;

public interface SignupService {

    void signup(SignupRequest request) throws InvalidSignupException;

}
