package com.hack_roll.hack_roll.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.hack_roll.hack_roll.model.AuthenticateUser;
import com.hack_roll.hack_roll.model.User;
import com.hack_roll.hack_roll.payload.JwtResponse;
import com.hack_roll.hack_roll.repository.UserRepository;
import com.hack_roll.hack_roll.service.JwtService;
import com.hack_roll.hack_roll.dto.AuthenticateUserDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;

@PostMapping("/signin")
public ResponseEntity<JwtResponse> authenticateUser(@RequestBody AuthenticateUser user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword()
                )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails.getUsername());
        return ResponseEntity.ok(new JwtResponse("User has signed in correctly", token));
    }

@PostMapping("/signup")
public ResponseEntity<?> registerUser(@Valid @RequestBody AuthenticateUserDTO userDTO) {
    if (userRepository.existsByEmail(userDTO.getEmail())) {
        return ResponseEntity.badRequest().body("Error: Email is already taken!");
    }

    User newUser = new User(
        userDTO.getFirstName(),
        userDTO.getLastName(),
        userDTO.getEmail(),
        encoder.encode(userDTO.getPassword())
    );

    userRepository.save(newUser);
    return ResponseEntity.ok("User registered successfully!");
}

@PostMapping("/signout")
//Este signout es simbólico: no afecta el token.
public ResponseEntity<?> logoutUser(@RequestHeader(value = "Authorization", required = false) String authHeader) {
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No token found to sign out");
    }
    return ResponseEntity.ok("User has been signed out.");
}
}
