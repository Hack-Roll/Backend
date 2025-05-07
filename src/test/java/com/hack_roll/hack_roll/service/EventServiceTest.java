package com.hack_roll.hack_roll.service;

import com.hack_roll.hack_roll.dto.EventUpdateRequest;
import com.hack_roll.hack_roll.model.Event;
import com.hack_roll.hack_roll.model.User;
import com.hack_roll.hack_roll.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @Test
    void createEvent_ShouldSaveAndReturnEvent() {
        // Arrange
        Event eventToCreate = new Event();
        eventToCreate.setTitle("Test Event");
        Event savedEvent = new Event();
        savedEvent.setId(1L);
        savedEvent.setTitle("Test Event");

        when(eventRepository.save(eventToCreate)).thenReturn(savedEvent);

        // Act
        Event createdEvent = eventService.createEvent(eventToCreate);

        // Assert
        assertNotNull(createdEvent);
        assertEquals(1L, createdEvent.getId());
        assertEquals("Test Event", createdEvent.getTitle());
        verify(eventRepository, times(1)).save(eventToCreate);
    }

    @Test
    void getEventById_EventFound_ShouldReturnOptionalWithEvent() {
        // Arrange
        Long eventId = 1L;
        Event event = new Event();
        event.setId(eventId);
        event.setTitle("Found Event");

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        // Act
        Optional<Event> retrievedEvent = eventService.getEventById(eventId);

        // Assert
        assertTrue(retrievedEvent.isPresent());
        assertEquals(eventId, retrievedEvent.get().getId());
        assertEquals("Found Event", retrievedEvent.get().getTitle());
        verify(eventRepository, times(1)).findById(eventId);
    }

    @Test
    void getEventById_EventNotFound_ShouldReturnEmptyOptional() {
        // Arrange
        Long eventId = 1L;
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        // Act
        Optional<Event> retrievedEvent = eventService.getEventById(eventId);

        // Assert
        assertTrue(retrievedEvent.isEmpty());
        verify(eventRepository, times(1)).findById(eventId);
    }

    @Test
    void getAllEvents_ShouldReturnPageOfEvents() {
        // Arrange
        List<Event> events = List.of(new Event(), new Event());
        Page<Event> eventPage = new PageImpl<>(events);
        Specification<Event> filters = null;
        Pageable paging = Pageable.unpaged();

        when(eventRepository.findAll(filters, paging)).thenReturn(eventPage);

        // Act
        Page<Event> resultPage = eventService.getAllEvents(filters, paging);

        // Assert
        assertEquals(2, resultPage.getContent().size());
        verify(eventRepository, times(1)).findAll(filters, paging);
    }

    @Test
    void deleteEvent_ShouldCallDeleteOnRepository() {
        // Arrange
        Event eventToDelete = new Event();

        // Act
        eventService.deleteEvent(eventToDelete);

        // Assert
        verify(eventRepository, times(1)).delete(eventToDelete);
    }

    @Test
    void updateEvent_ShouldUpdateFieldsAndSave() {
        // Arrange
        Event existingEvent = new Event();
        existingEvent.setTitle("Old Title");
        existingEvent.setDescription("Old Description");
        existingEvent.setDate(LocalDateTime.now().minusDays(1));
        existingEvent.setLocation("Old Location");
        existingEvent.setMaxAttendees(10);

        EventUpdateRequest updates = new EventUpdateRequest();
        updates.setTitle("New Title");
        updates.setDescription("New Description");
        updates.setDate(LocalDateTime.now());
        updates.setLocation("New Location");
        updates.setMaxAttendees(20);

        when(eventRepository.save(existingEvent)).thenReturn(existingEvent);

        // Act
        Event updatedEvent = eventService.updateEvent(existingEvent, updates);

        // Assert
        assertEquals("New Title", updatedEvent.getTitle());
        assertEquals("New Description", updatedEvent.getDescription());
        assertEquals("New Location", updatedEvent.getLocation());
        assertEquals(20, updatedEvent.getMaxAttendees());
        verify(eventRepository, times(1)).save(existingEvent);
    }

    @Test
    void updateEvent_ShouldHandleNullUpdates() {
        // Arrange
        Event existingEvent = new Event();
        existingEvent.setTitle("Original Title");
        existingEvent.setDescription("Original Description");
        existingEvent.setLocation("Original Location");
        existingEvent.setMaxAttendees(10);

        EventUpdateRequest updates = new EventUpdateRequest(); // All fields are null

        when(eventRepository.save(existingEvent)).thenReturn(existingEvent);

        // Act
        Event updatedEvent = eventService.updateEvent(existingEvent, updates);

        // Assert
        assertEquals("Original Title", updatedEvent.getTitle());
        assertEquals("Original Description", updatedEvent.getDescription());
        assertEquals("Original Location", updatedEvent.getLocation());
        assertEquals(10, updatedEvent.getMaxAttendees());
        verify(eventRepository, times(1)).save(existingEvent);
    }
}