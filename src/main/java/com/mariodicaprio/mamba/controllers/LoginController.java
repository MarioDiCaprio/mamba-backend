package com.mariodicaprio.mamba.controllers;


import com.mariodicaprio.mamba.exceptions.InvalidLoginException;
import com.mariodicaprio.mamba.services.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Transactional
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    //////////////////////////////////////////////////////////////////////////

    @PostMapping
    @Operation(description = "Attempts to login. If the attempt was successful, returns a JWT token.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login was successful"),
            @ApiResponse(responseCode = "401", description = "Login was invalid")
    })
    private String login(Authentication authentication) throws InvalidLoginException {
        return loginService.login(authentication);
    }

}
