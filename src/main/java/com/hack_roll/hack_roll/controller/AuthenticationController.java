package com.hack_roll.hack_roll.controller;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.hack_roll.hack_roll.model.AuthenticateUser;
import com.hack_roll.hack_roll.model.Event;
import com.hack_roll.hack_roll.model.User;
import com.hack_roll.hack_roll.payload.JwtResponse;
import com.hack_roll.hack_roll.repository.UserRepository;
import com.hack_roll.hack_roll.service.EventService;
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
    @Autowired
    private EventService eventService;

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
    newUser.setPhoto(userDTO.getPhoto());

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


@GetMapping("/users/{id}")
public ResponseEntity<User> getSingleUser(@PathVariable Long id) {
    Optional<User> userOptional = userRepository.findById(id);
    if (userOptional.isPresent()) {
        return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
    } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

@GetMapping("/users")
public ResponseEntity<?> getAllUsers(@RequestHeader("Authorization") String authHeader) {
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid authorization header");
    }

    String token = authHeader.substring(7);

    if (!jwtService.validateJwtToken(token)) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
    }

    String email = jwtService.getUsernameFromToken(token);

    if (!"admin@admin.com".equalsIgnoreCase(email)) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied - user does not have the required permissions to access this information");
    }

    List<User> users = userRepository.findAll();
    return ResponseEntity.ok(users);
}


@GetMapping("/user/events")
public ResponseEntity<List<Event>> getEventsByCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
    String userEmail = userDetails.getUsername();
    List<Event> userEvents = eventService.getEventsByUserEmail(userEmail);
    return ResponseEntity.ok(userEvents);
}


@PutMapping("/users")
// para update un usuario, el mismo usuario tiene que estar logeado y añadir el token en el request de postman
public ResponseEntity<?> updateUser(
    @RequestBody UpdateUserDTO updateDTO,
    @RequestHeader("Authorization") String authHeader) {

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body("Missing or invalid Authorization header");
    }

    String token = authHeader.substring(7);

    if (!jwtService.validateJwtToken(token)) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body("Invalid or expired token");
    }

    String email = jwtService.getUsernameFromToken(token);
    User user = userRepository.findByEmail(email);
    
    if (user == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    if (updateDTO.getFirstName() != null) {
        user.setFirstName(updateDTO.getFirstName());
    }

    if (updateDTO.getLastName() != null) {
        user.setLastName(updateDTO.getLastName());
    }

    if (updateDTO.getPassword() != null) {
        user.setPassword(encoder.encode(updateDTO.getPassword()));
    }

    userRepository.save(user);

    return ResponseEntity.ok("User updated successfully");
}


@DeleteMapping("/users/{id}")
public ResponseEntity<?> deleteUser(
        @PathVariable Long id,
        @RequestHeader("Authorization") String authHeader) {

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid authorisation header");
    }

    String token = authHeader.substring(7);

    if (!jwtService.validateJwtToken(token)) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
    }

    String emailFromToken = jwtService.getUsernameFromToken(token);
    User requestingUser = userRepository.findByEmail(emailFromToken);
    if (requestingUser == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Authenticated user not found");
    }

    User userToDelete = userRepository.findById(id).orElse(null);
    if (userToDelete == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User to delete not found");
    }

    // Solo usuario mismo o admin puede borrar
    if (!userToDelete.getEmail().equals(emailFromToken) && 
        !emailFromToken.equals("admin@admin.com")) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not authorised to delete this user");
    }

    userRepository.delete(userToDelete);
    return ResponseEntity.ok("User deleted successfully");
}
}
