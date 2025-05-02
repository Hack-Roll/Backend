package com.hack_roll.hack_roll.model;

public class AuthenticateUser {
    private String email;
    private String password;
    
    public AuthenticateUser() {
    }

    public AuthenticateUser(String email,String password) {
      
        this.password = password;
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
