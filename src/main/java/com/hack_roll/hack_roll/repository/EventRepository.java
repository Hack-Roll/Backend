package com.hack_roll.hack_roll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hack_roll.hack_roll.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    
    
}