package com.blogapp.blogapp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {
    //TODO: Move the key to separate .properties file
    private static String JWT_KEY = "aefdasferaghgregasdfgasfrfe4qftrew";
    private Algorithm algorithm = Algorithm.HMAC256(JWT_KEY);

    public String createJWT(Long userId) {
        return JWT.create()
                .withSubject(userId.toString())
                .withIssuedAt(new Date())
                //.withExpiresAt() //TODO: setup expiry parameter
                .sign(algorithm);
    }

    public Long retrieveUserId(String jwtString) {
        var decodedJwt = JWT.decode(jwtString);
        var userId = Long.valueOf(decodedJwt.getSubject());
        return userId;
    }
}
