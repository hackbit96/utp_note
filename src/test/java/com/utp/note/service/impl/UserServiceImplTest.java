package com.utp.note.service.impl;

import com.utp.note.domain.User;
import com.utp.note.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setEmail("orlando.camavilca@example.com");
        user.setPassword("password123");
    }

    @Test
    public void testUserDetailsService_WhenUserExists() {
        when(userRepository.findByEmail("orlando.camavilca@example.com")).thenReturn(Optional.of(user));

        UserDetailsService userDetailsService = userService.userDetailsService();
        assertDoesNotThrow(() -> userDetailsService.loadUserByUsername("orlando.camavilca@example.com"));
    }

    @Test
    public void testUserDetailsService_WhenUserDoesNotExist() {
        when(userRepository.findByEmail("orlando.camavilca@example.com")).thenReturn(Optional.empty());

        UserDetailsService userDetailsService = userService.userDetailsService();
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("orlando.camavilca@example.com"));
    }

    @Test
    public void testGetByEmail_WhenUserExists() {
        when(userRepository.findByEmail("orlando.camavilca@example.com")).thenReturn(Optional.of(user));

        User result = userService.getByEmail("orlando.camavilca@example.com");

        assertNotNull(result);
        assertEquals("orlando.camavilca@example.com", result.getEmail());
    }

    @Test
    public void testGetByEmail_WhenUserDoesNotExist() {
        when(userRepository.findByEmail("orlando.camavilca@example.com")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userService.getByEmail("orlando.camavilca@example.com"));
    }
}
