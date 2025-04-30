package com.hack_roll.hack_roll.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UserDTO {

    @NotBlank(message = "An email address is required")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", message = "Must have valid email address format")
    private String email;

    @NotBlank(message = "A password is required")
    @Pattern(regexp = "^[a-zA-Z0-9_.-]+$", message = "Only letters, numbers and certain symbols (_, ., -) are allowed")
    private String password;

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
