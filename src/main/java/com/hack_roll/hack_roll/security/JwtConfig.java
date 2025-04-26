package com.hack_roll.hack_roll.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class JwtConfig {
private final Dotenv dotenv = Dotenv.load();

    @Bean
    public String getJwtSecret() {
        return dotenv.get("JWT_SECRET");
    }

}