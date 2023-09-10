package com.example.mobilele.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.mobilele.exceptions.NotFoundException;
import com.example.mobilele.exceptions.WrongCredentialsException;
import com.example.mobilele.utils.EntityHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component

public class JwtUtil {
    private final String secret;
    private final EntityHelper entityHelper;


    public JwtUtil(@Value("${api.security-secret-key}") String secret, EntityHelper entityHelper) {
        this.secret = secret;
        this.entityHelper = entityHelper;
    }

    public String createJwtToken(String userId) {
        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withExpiresAt(Instant.now().plus(Duration.of(30, ChronoUnit.MINUTES)))
                .sign(Algorithm.HMAC256(secret));
    }

    public DecodedJWT decodeToken(String token) {

        return JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(token);
    }

    @Transactional
    public UserPrincipal convert(DecodedJWT token) {
        try {
            UserPrincipal user = new UserPrincipal(entityHelper.findUserById(token.getSubject()));
            user.setJwtToken(token.getToken());
            return user;
        } catch (NotFoundException | WrongCredentialsException e) {
            return null;
        }
    }
}