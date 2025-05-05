package com.hack_roll.hack_roll.service;

//import java.util.Date;
//import java.util.List;
//import java.util.Date;
//import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

//import com.hack_roll.hack_roll.dto.EventFilterRequest;
//import com.hack_roll.hack_roll.dto.EventSpecifications;
//import com.hack_roll.hack_roll.dto.EventFilterRequest;
//import com.hack_roll.hack_roll.dto.EventSpecifications;
import com.hack_roll.hack_roll.dto.EventUpdateRequest;
import com.hack_roll.hack_roll.model.Event;
import com.hack_roll.hack_roll.model.User;
import com.hack_roll.hack_roll.repository.EventRepository;

import jakarta.transaction.Transactional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    @Transactional
    public void addAttendee(Event event, User user) {
        event.getAttendees().add(user);
        eventRepository.save(event);
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public Page<Event> getAllEvents(Specification<Event> filters, Pageable paging) {

        return eventRepository.findAll(filters, paging);
    }

    public void deleteEvent(Event event) {
        eventRepository.delete(event);
    }

    public Event updateEvent(Event event, EventUpdateRequest updates) {

        if (updates.getTitle() != null) {
            event.setTitle(updates.getTitle());
        }

        if (updates.getDescription() != null) {
            event.setDescription(updates.getDescription());
        }

        if (updates.getDate() != null) {
            event.setDate(updates.getDate());
        }

        if (updates.getLocation() != null) {
            event.setLocation(updates.getLocation());
        }

        if (updates.getMaxAttendees() != null) {
            event.setMaxAttendees(updates.getMaxAttendees());
        }

        return eventRepository.save(event);
    }
}