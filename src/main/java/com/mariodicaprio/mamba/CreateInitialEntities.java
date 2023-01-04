package com.mariodicaprio.mamba;


import com.mariodicaprio.mamba.requests.SignupRequest;
import com.mariodicaprio.mamba.services.SignupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
@Log4j2
@RequiredArgsConstructor
public class CreateInitialEntities implements CommandLineRunner {

    private final SignupService signupService;

    ////////////////////////////////////////////////////////////////////////////

    @Override
    public void run(String... args) throws Exception {
        log.info("Registering initial users");
        signupService.signup(new SignupRequest("user", "user@gmail.com", "password"));
    }

}
