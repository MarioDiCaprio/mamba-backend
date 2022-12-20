package com.mariodicaprio.mamba.exceptions;


import lombok.experimental.StandardException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@StandardException
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Username already exists")
public class InvalidSignupException extends Exception {

}
