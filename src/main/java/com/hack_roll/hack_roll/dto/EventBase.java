package com.hack_roll.hack_roll.dto;

import java.time.LocalDateTime;

public class EventBase {
    private Long id;
    private String title; 
    private String description;
    private LocalDateTime date;
    private String location;
    private String category;

    public EventBase (Long id, String title, String description, LocalDateTime date, String location, String category) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.location = location;
        this.category = category;
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public LocalDateTime getDate() {
        return this.date;
    }

    public String getLocation() {
        return this.location;
    }

    public String getCategory() {
        return this.category;
    }


}
