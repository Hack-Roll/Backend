package com.hack_roll.hack_roll.payload;

public class JwtResponse {
    private String message;
    private String token;

    public JwtResponse(String message, String token) {
        this.message = message;
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }
}
