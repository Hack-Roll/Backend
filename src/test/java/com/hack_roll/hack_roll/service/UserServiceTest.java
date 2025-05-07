package com.hack_roll.hack_roll.service;

import com.hack_roll.hack_roll.model.User;
import com.hack_roll.hack_roll.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void getUserById_UserFound() {
      
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setEmail("test@example.com");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

      
        Optional<User> retrievedUser = userService.getUserById(userId);

        
        assertTrue(retrievedUser.isPresent());
        assertEquals(userId, retrievedUser.get().getId());
        assertEquals("test@example.com", retrievedUser.get().getEmail());
    }

    @Test
    void getUserById_UserNotFound() {
       
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

       
        Optional<User> retrievedUser = userService.getUserById(userId);

      
        assertTrue(retrievedUser.isEmpty());
    }
}