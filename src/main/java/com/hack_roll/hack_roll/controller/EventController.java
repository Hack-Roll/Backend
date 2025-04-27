package com.hack_roll.hack_roll.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.*;

import com.hack_roll.hack_roll.model.Event;
import com.hack_roll.hack_roll.model.User;
import com.hack_roll.hack_roll.repository.EventRepository;
import com.hack_roll.hack_roll.repository.UserRepository;

@RestController
@RequestMapping("/api/user/event")
public class EventController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EventRepository eventRepository;
   
    @PostMapping("")
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            // 2. Extract the username (or principal) from the authentication object
            String email = authentication.getName();

            // 3. Retrieve the User object from the database using the username
            User createdBy = userRepository.findByEmail(email);

            // 4. Set the createdBy user for the event
            event.setCreatedBy(createdBy);

            // 5. Save the event using the EventService
            Event savedEvent = eventRepository.save(event);

            return new ResponseEntity<Event>(savedEvent, HttpStatus.CREATED);
         } else {
            return new ResponseEntity<Event>(HttpStatus.UNAUTHORIZED);
                }
}
}
