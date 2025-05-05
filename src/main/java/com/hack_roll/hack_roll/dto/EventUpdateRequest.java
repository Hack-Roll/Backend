package com.hack_roll.hack_roll.dto;

import java.time.LocalDateTime;

public class EventUpdateRequest {
    private String title;
    private String description;
    private LocalDateTime date;
    private String location;
    private Integer maxAttendees;


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

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getMaxAttendees() {
        return this.maxAttendees;
    }

    public void setMaxAttendees(Integer maxAttendees) {
        this.maxAttendees = maxAttendees;
    }
}