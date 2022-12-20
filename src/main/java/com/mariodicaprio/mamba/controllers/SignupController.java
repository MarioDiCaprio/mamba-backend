package com.mariodicaprio.mamba.controllers;


import com.mariodicaprio.mamba.exceptions.InvalidSignupException;
import com.mariodicaprio.mamba.requests.SignupRequest;
import com.mariodicaprio.mamba.services.SignupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignupController {

    private final SignupService signupService;

    ////////////////////////////////////////////////////////////////

    @PostMapping
    @Transactional
    @Operation(description = "Performs a signup operation")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Signed up successfully"),
            @ApiResponse(responseCode = "409", description = "Username is already in use")
    })
    void signup(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The signup request")
            SignupRequest request
    ) throws InvalidSignupException {
        signupService.signup(request);
    }

}
