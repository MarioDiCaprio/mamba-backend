package com.mariodicaprio.mamba.exceptions;


import lombok.experimental.StandardException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@StandardException
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Invalid login credentials")
public class InvalidLoginException extends Exception {

}
