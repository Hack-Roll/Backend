package com.hack_roll.hack_roll.controller;

//import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.hack_roll.hack_roll.dto.EventFilterRequest;
import com.hack_roll.hack_roll.dto.EventSpecifications;
import com.hack_roll.hack_roll.model.Event;
//import com.hack_roll.hack_roll.model.User;
import com.hack_roll.hack_roll.service.EventService;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/event")
public class PublicEventController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    EventService eventService;
   
    @GetMapping("")
    public ResponseEntity<Page<Event>> getAllEvents(@ModelAttribute EventFilterRequest filterRequest) {
        Specification<Event> filters = Specification.where(null);

        if (filterRequest.getTitle() != null && !filterRequest.getTitle().isEmpty()) {
            filters = filters.and(EventSpecifications.hasTitle(filterRequest.getTitle()));
        }
        if (filterRequest.getCategory() != null && !filterRequest.getCategory().isEmpty()) {
            filters = filters.and(EventSpecifications.hasCategory(filterRequest.getCategory()));
        }
        if (filterRequest.getDate() != null) {
            filters = filters.and(EventSpecifications.hasDate(filterRequest.getDate()));
        }
        if (filterRequest.getCreatedBy() != null && !filterRequest.getCreatedBy().isEmpty()) {
            filters = filters.and(EventSpecifications.createdByUserName(filterRequest.getCreatedBy()));
        }

        Pageable paging = PageRequest.of(filterRequest.getPage(), filterRequest.getSize());

        Page<Event> events = eventService.getAllEvents(filters, paging);

        return ResponseEntity.ok(events);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Event> getSingleEvent(@PathVariable Long eventId) {
        Optional<Event> eventOptional = eventService.getEventById(eventId);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            return new ResponseEntity<Event>(event, HttpStatus.OK);
        } else {
            return new ResponseEntity<Event>(HttpStatus.NOT_FOUND);
        }
     }
}