package com.hack_roll.hack_roll.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.hack_roll.hack_roll.security.JwtConfig;

import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.security.Keys;

class JwtServiceTest {

    private JwtService jwtService;
    private JwtConfig jwtConfig;
    private SecretKey testKey;
    private String testSecret = "bb0f58f37c0ffefab3d9f07dbfde237f243bfe734441d9e4152dcc0f4a16885dea96f192fe033adfa2593a0fb9136d3a88927891c229f3235db443fd6252ad331015c1c2b337d75db4a963a44e3f86775f26218694d3b7e970f0e5d4310157010e4c614211d7fb6669a260130c97b23cf439546f3bcfce6531bc6f7504d7bfab";

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        jwtConfig = mock(JwtConfig.class);
        when(jwtConfig.getJwtSecret()).thenReturn(testSecret);
        ReflectionTestUtils.setField(jwtService, "jwtConfig", jwtConfig);
        jwtService.init();
        testKey = Keys.hmacShaKeyFor(testSecret.getBytes(StandardCharsets.UTF_8));
    }

    @Test
    void generateToken_validEmail_returnsNonEmptyToken() {
        String token = jwtService.generateToken("test@example.com");
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void getUsernameFromToken_validToken_returnsCorrectUsername() {
        String email = "user@example.com";
        String token = jwtService.generateToken(email);
        String username = jwtService.getUsernameFromToken(token);
        assertEquals(email, username);
    }

    @Test
    void validateJwtToken_validToken_returnsTrue() {
        String token = jwtService.generateToken("valid@example.com");
        assertTrue(jwtService.validateJwtToken(token));
    }

    @Test
    void validateJwtToken_expiredToken_returnsFalse() throws InterruptedException {
        // Generate a token that is already expired
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() - 1000);
        String expiredToken = Jwts.builder()
                .setSubject("expired@example.com")
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(testKey, SignatureAlgorithm.HS512)
                .compact();
        assertFalse(jwtService.validateJwtToken(expiredToken));
    }
   
    @Test
    void validateJwtToken_malformedToken_returnsFalse() {
        String malformedToken = "this.is.not.a.valid.jwt";
        assertFalse(jwtService.validateJwtToken(malformedToken));
    }

    @Test
    void validateJwtToken_unsupportedToken_returnsFalse() {
        // While it's difficult to create a truly "unsupported" token with standard libraries,
        // we can simulate a scenario where the parser might encounter an unexpected format.
        String unsupportedToken = "header.payload";
        assertFalse(jwtService.validateJwtToken(unsupportedToken));
    }

    @Test
    void validateJwtToken_emptyClaims_returnsFalse() {
        String emptyClaimsToken = Jwts.builder()
                .signWith(testKey, SignatureAlgorithm.HS512)
                .compact();
        assertFalse(jwtService.validateJwtToken(emptyClaimsToken));
    }

    @Test
    void getUserById_alwaysThrowsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> jwtService.getUserById(1L));
    }
}
