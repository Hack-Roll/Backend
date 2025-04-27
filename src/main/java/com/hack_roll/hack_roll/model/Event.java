package com.hack_roll.hack_roll.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name= "events")
public class Event {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "created_by_user_id", nullable = false)
    private User createdBy;
    @Column(nullable = false)
    private String title; 
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private LocalDateTime date;
    @Column(nullable = false)
    private int maxAttendees = 1;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private String category;


    public Event() {
    }

    public Event(User createdBy, String title, String description, LocalDateTime date, int maxAttendees, String location, String category) {
        this.createdBy = createdBy;
        this.title = title;
        this.description = description;
        this.date = date;
        this.maxAttendees = maxAttendees;
        this.location = location;
        this.category = category;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return this.date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getMaxAttendees() {
        return this.maxAttendees;
    }

    public void setMaxAttendees(int maxAttendees) {
        this.maxAttendees = maxAttendees;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


}



