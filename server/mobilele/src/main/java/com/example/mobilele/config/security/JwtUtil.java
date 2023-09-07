package com.example.mobilele.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.mobilele.exceptions.UserNotFoundException;
import com.example.mobilele.services.UserServiceImp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class JwtUtil {
    private final String secret;

    private final UserServiceImp userService;

    public JwtUtil(@Value("${api.security-secret-key}") String secret, UserServiceImp userService) {
        this.secret = secret;
        this.userService = userService;
    }

    public String createJwtToken(String userId){
        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withExpiresAt(Instant.now().plus(Duration.of(30, ChronoUnit.MINUTES)))
                .sign(Algorithm.HMAC256(secret));
    }

    public DecodedJWT decodeToken(String token){
        return JWT.require(Algorithm.HMAC256(secret))
                    .build()
                    .verify(token);


    }

    public UserPrincipal convert(DecodedJWT token) throws UserNotFoundException {
        UserPrincipal user = new UserPrincipal(userService.getById(token.getSubject()));
        user.setJwtToken(token.getToken());
        return user;

    }
}