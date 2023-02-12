package com.mariodicaprio.mamba.services;


import com.mariodicaprio.mamba.exceptions.InvalidLoginException;
import com.mariodicaprio.mamba.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Log4j2
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenService tokenService;

    //////////////////////////////////////////////////////////////////////////

    @Override
    public boolean isLoginValid(Authentication authentication) {
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        String password = ((UserDetails) authentication.getPrincipal()).getPassword();

        log.info("Checking validity of login credentials");
        if (username == null || password == null) {
            log.warn("Username or password is null");
            return false;
        }
        var user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            log.warn("Username invalid");
            return false;
        }
        var valid = user.getPassword().equals(password) || passwordEncoder.matches(password, user.getPassword());
        if (valid)
            log.info("Login credentials are valid");
        else
            log.warn("Login credentials are invalid");
        return valid;
    }

    @Override
    public String login(Authentication authentication) throws InvalidLoginException {
        log.info("Initiating login attempt");
        if (isLoginValid(authentication)) {
            log.info("Login successful");
            return tokenService.generateToken(authentication);
        } else {
            log.info("Login unsuccessful");
            throw new InvalidLoginException();
        }
    }

}
