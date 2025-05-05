package com.hack_roll.hack_roll.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name= "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

        @NotBlank(message = "A name is required")
        private String firstName;
    
        @NotBlank(message = "A last name is required")
        private String lastName;

    @Column(unique = true)
    @NotBlank(message = "An email address is required")
    @Email(message = "Must have valid email address format")
    private String email;

    @NotBlank(message = "A password is required")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    //Add photo

    @OneToMany(mappedBy = "createdBy")
    private Set<Event> createdEvents = new HashSet<>();
    @ManyToMany(mappedBy = "attendees")
    private Set<Event> attendedEvents = new HashSet<>();
    
    public User() {
    }

    public User(String firstName, String lastName, String email,String encodedPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = encodedPassword;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Event> getCreatedEvents() {
        return this.createdEvents;
    }

    public void setCreatedEvents(Set<Event> createdEvents) {
        this.createdEvents = createdEvents;
    }

    public Set<Event> getAttendedEvents() {
        return this.attendedEvents;
    }

    public void setAttendedEvents(Set<Event> attendedEvents) {
        this.attendedEvents = attendedEvents;
    }
}
