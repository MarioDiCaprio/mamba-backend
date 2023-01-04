package com.mariodicaprio.mamba.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final JwtEncoder jwtEncoder;

    ///////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public String generateToken(Authentication authentication) {
        log.info("JWT-Token generation started");

        Instant now = Instant.now();
        Instant expiration = now.plus(7, ChronoUnit.DAYS);

        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(expiration)
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();

        JwtEncoderParameters params = JwtEncoderParameters.from(claims);

        log.info("JWT-Token generation finished");

        return jwtEncoder.encode(params).getTokenValue();
    }

}
