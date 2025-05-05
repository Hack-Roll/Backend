package com.hack_roll.hack_roll.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.*;

import com.hack_roll.hack_roll.dto.Attendee;
import com.hack_roll.hack_roll.dto.EventUpdateRequest;
import com.hack_roll.hack_roll.model.Event;
import com.hack_roll.hack_roll.model.User;
import com.hack_roll.hack_roll.repository.UserRepository;
import com.hack_roll.hack_roll.service.EventService;



@RestController
@CrossOrigin(origins = "*")
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

     // unattend event
     //
     @DeleteMapping("/{eventId}/unattend")
    public ResponseEntity<Event> removeAttendee(@PathVariable Long eventId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email);
            Optional<Event> eventOptional = eventService.getEventById(eventId);

            if (eventOptional.isPresent()) {
                Event event = eventOptional.get();
                eventService.removeAttendee(event, user);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    //
    @GetMapping("/{eventId}/attendees")
    public ResponseEntity<List<Attendee>> showAttendees(@PathVariable Long eventId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Optional<Event> eventOptional = eventService.getEventById(eventId);

            if (eventOptional.isPresent()) {
                Event event = eventOptional.get();

                List<Attendee> attendees = event.getAttendees().stream()
                .map(user -> new Attendee(user.getFirstName(), user.getLastName()))
                .collect(Collectors.toList());
                return new ResponseEntity<List<Attendee>>(attendees, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }



    //

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

    @PutMapping("/{eventId}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long eventId, @RequestBody EventUpdateRequest updates) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || ! authentication.isAuthenticated()) {
            return new ResponseEntity<Event>(HttpStatus.UNAUTHORIZED);
        }

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
        
        Event updatedEvent = eventService.updateEvent(event, updates);
        
        return new ResponseEntity<Event>(updatedEvent, HttpStatus.OK);
    }
}
