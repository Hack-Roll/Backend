package com.hack_roll.hack_roll.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.*;

import com.hack_roll.hack_roll.model.Event;
import com.hack_roll.hack_roll.model.User;
import com.hack_roll.hack_roll.repository.UserRepository;
import com.hack_roll.hack_roll.service.EventService;


@RestController
@RequestMapping("/api/user/event")
public class EventController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EventService eventService;
   
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
            Event savedEvent = eventService.createEvent(event);

            return new ResponseEntity<Event>(savedEvent, HttpStatus.CREATED);
         } else {
            return new ResponseEntity<Event>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/{eventId}/attend")
    public ResponseEntity<Event> addAttendee(@PathVariable Long eventId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
 
         if (authentication != null && authentication.isAuthenticated()) {
             // 2. Extract the username (or principal) from the authentication object
             String email = authentication.getName();
 
             // 3. Retrieve the User object from the database using the username
             User user = userRepository.findByEmail(email);
             Optional<Event> eventOptional = eventService.getEventById(eventId);
             
             if (eventOptional.isPresent()) {
                Event event = eventOptional.get();
              
                eventService.addAttendee(event, user);
              
                return new ResponseEntity<Event>(HttpStatus.OK);
              } else {
                return new ResponseEntity<Event>(HttpStatus.NOT_FOUND);
              }
          } else {
             return new ResponseEntity<Event>(HttpStatus.UNAUTHORIZED);
         }
     }

     @DeleteMapping("/{eventId}")
     public ResponseEntity<Event> deleteEvent(@PathVariable Long eventId) {
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
  
          if (authentication != null && authentication.isAuthenticated()) {
              // 2. Extract the username (or principal) from the authentication object
              String email = authentication.getName();
  
              // 3. Retrieve the User object from the database using the username
              User user = userRepository.findByEmail(email);
              Optional<Event> eventOptional = eventService.getEventById(eventId);
              
              if (! eventOptional.isPresent()) {
                return new ResponseEntity<Event>(HttpStatus.NOT_FOUND);
              }

              Event event = eventOptional.get();
              if (user.getId() != event.getCreatedBy().getId()) {
                return new ResponseEntity<Event>(HttpStatus.UNAUTHORIZED);
              }
              
              eventService.deleteEvent(event);
              
              return new ResponseEntity<Event>(HttpStatus.OK);
           } else {
              return new ResponseEntity<Event>(HttpStatus.UNAUTHORIZED);
          }
      }
}
