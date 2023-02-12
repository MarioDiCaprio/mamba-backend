package com.mariodicaprio.mamba.services;


import com.mariodicaprio.mamba.entities.User;
import com.mariodicaprio.mamba.exceptions.InvalidSignupException;
import com.mariodicaprio.mamba.repositories.UserRepository;
import com.mariodicaprio.mamba.requests.SignupRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Log4j2
@Service
@RequiredArgsConstructor
public class SignupServiceImpl implements SignupService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    /////////////////////////////////////////////////////////////////////////////

    @Override
    @Transactional
    public void signup(SignupRequest request) throws InvalidSignupException {
        log.info("Starting signup with username \"{}\": ", request.getUsername());
        var usernameTaken = userRepository.existsByUsername(request.getUsername());
        if (usernameTaken) {
            log.warn("username \"" + request.getUsername() + "\" already in use");
            throw new InvalidSignupException();
        }
        var encPassword = passwordEncoder.encode(request.getPassword());
        var user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(encPassword);
        userRepository.save(user);
        log.info("User with username \"" + request.getUsername() + "\" signed up successfully");
    }

}
