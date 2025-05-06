package com.hack_roll.hack_roll.dto;

public class Attendee {
    private String firstName;
    private String lastName;

    public Attendee(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;

    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    
}
