package com.hack_roll.hack_roll.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}