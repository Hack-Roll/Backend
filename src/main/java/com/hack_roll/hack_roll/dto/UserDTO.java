package com.hack_roll.hack_roll.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UserDTO {

    //Add name
    @NotBlank(message = "First name cannot be blank")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s'-]+$", message = "Only certain characters are allowed")
    private String firstName;
    
    //Add last name
    @NotBlank(message = "First name cannot be blank")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s'-]+$", message = "Only certain characters are allowed")
    private String lastName;


    @NotBlank(message = "An email address is required")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", message = "Must have valid email address format")
    private String email;

    @NotBlank(message = "A password is required")
    @Pattern(regexp = "^[a-zA-Z0-9_.-ñÑ]+$", message = "Only letters, numbers and certain symbols (_, ., -) are allowed")
    private String password;

    // Getters and Setters
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

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
