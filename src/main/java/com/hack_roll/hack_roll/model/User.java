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
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name= "users")
public class User {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", message = "Must have valid email address format")
    private String email;
    @Pattern(regexp = "[a-zA-Z0-9_.-]+$", message = "Only letters, numbers and certain symbols (_, ., -)are allowed")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password; 
    @OneToMany(mappedBy = "createdBy")
    private Set<Event> createdEvents = new HashSet<>();
    @ManyToMany(mappedBy = "attendees")
    private Set<Event> attendedEvents = new HashSet<>();
    
    public User() {
    }

    public User(String email,String password) {
      
        this.password = password;
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
}
