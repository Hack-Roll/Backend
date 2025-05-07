package com.hack_roll.hack_roll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hack_roll.hack_roll.model.Event;
import com.hack_roll.hack_roll.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    boolean existsByEmail(String email);
    List<Event> findByUser(User user);
}

 