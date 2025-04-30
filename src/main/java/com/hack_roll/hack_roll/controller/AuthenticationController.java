package com.hack_roll.hack_roll.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.hack_roll.hack_roll.model.User;
import com.hack_roll.hack_roll.payload.JwtResponse;
import com.hack_roll.hack_roll.repository.UserRepository;
import com.hack_roll.hack_roll.service.JwtService;
import com.hack_roll.hack_roll.dto.UserDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    AuthenticationManager authenticationManager;
    // @Autowired
    // UserRepository userRepository;
    // @Autowired
    // PasswordEncoder encoder;
    @Autowired
    JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody User user) {
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

    // @PostMapping("/signup")
    // public String registerUser(@Valid @RequestBody User user) {
    //     if (userRepository.existsByEmail(user.getEmail())) {
    //         return "Error: Email is already taken!";
    //     }
    //     // Create new user's account
    //     User newUser = new User(
    //             user.getEmail(),
    //             encoder.encode(user.getPassword())
    //     );
    //     userRepository.save(newUser);
    //     return "User registered successfully!";
    // }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already taken!");
        }

        User newUser = new User(userDTO.getEmail(), encoder.encode(userDTO.getPassword()));
        userRepository.save(newUser);

        return ResponseEntity.ok("User registered successfully!");
    }
}
