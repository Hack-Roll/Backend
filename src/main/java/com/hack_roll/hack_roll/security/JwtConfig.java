package com.hack_roll.hack_roll.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtConfig {

    @Value("${JWT_SECRET}")
    private String jwtSecret;

    public String getJwtSecret() {
        return jwtSecret;
    }

}