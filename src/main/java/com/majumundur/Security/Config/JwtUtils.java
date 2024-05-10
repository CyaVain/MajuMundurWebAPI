package com.majumundur.Security.Config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.majumundur.Security.Models.UserCredentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@Slf4j // Annotasi Lombok Untuk Logging
public class JwtUtils {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long expiredInSecond;

    @Value("${jwt.app.name}")
    private String appName;

    public String generateToken(UserCredentials user){
        try{
            List<String> roles = user.getRoles()
                    .stream()
                    .map(role -> role.getRole().name()).toList();

            return JWT
                    .create()
                    .withIssuer(appName)
                    .withSubject(user.getId())
                    .withExpiresAt(Instant.now().plusSeconds(expiredInSecond))
                    //withClaim idk
                    .withClaim("roles",roles)
                    .sign(Algorithm.HMAC512(secretKey));
        }catch (JWTCreationException exception){
            log.error("Error Creating JWT Token : {}",exception.getMessage());
            throw new RuntimeException(exception);
        }
    }

    public boolean verifyToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            return decodedJWT.getIssuer().equals(appName);
        }catch (JWTVerificationException exception){
            log.error("Error JWT Verification : {}",exception.getMessage());
            return false;
        }
    }

    public JwtClaim getUserInfoByToken (String token){
        try{
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);

            List<String> roles = decodedJWT.getClaim("roles").asList(String.class);

            return JwtClaim.builder()
                    .userId(decodedJWT.getSubject())
                    .roles(roles)
                    .build();

        }catch (JWTVerificationException e){
            log.error("Invalid JWT Verification : {}",e.getMessage());
            return null;
        }
    }
}
