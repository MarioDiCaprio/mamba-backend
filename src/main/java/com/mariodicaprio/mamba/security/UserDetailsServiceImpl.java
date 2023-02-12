package com.mariodicaprio.mamba.security;


import com.mariodicaprio.mamba.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@Log4j2
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    //////////////////////////////////////////////////////////////////////////

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Fetching user details for username \"" + username + "\"");
        var user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            log.warn("User with username \"" + username + "\" not found");
            throw new UsernameNotFoundException(username);
        }
        log.info("User with username \"" + username + "\" found successfully");
        return new UserDetailsImpl(user);
    }

}
