package com.hack_roll.hack_roll.service;

import com.hack_roll.hack_roll.model.User;
import com.hack_roll.hack_roll.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
}