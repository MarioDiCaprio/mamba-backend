package com.mariodicaprio.mamba.services;


import com.mariodicaprio.mamba.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Log4j2
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    //////////////////////////////////////////////////////////////////////////

    @Override
    public boolean isLoginValid(String username, String password) {
        log.info("Checking validity of login credentials");
        if (username == null || password == null) {
            log.trace("Username or password is null");
            return false;
        }
        var user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            log.trace("Username invalid");
            return false;
        }
        return passwordEncoder.matches(password, user.getPassword());
    }

}
