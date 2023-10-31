package com.blogapp.blogapp.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JWTServiceTest {

    JWTService jwtService = new JWTService();

    @Test
    void canCreateJwtFormUserId () {
        var jwt = jwtService.createJWT(1001L);

        Assertions.assertNotNull(jwt);
    }
}
