package com.example.taskmanager.service.Implementations;

import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser() {
        User saved = new User(1L, "john", "pass123");

        when(userRepository.save(any(User.class))).thenReturn(saved);

        User result = userService.registerUser("john", "pass123");
        assertEquals(saved, result);
    }

    @Test
    void testLogin_Success() {
        User user = new User(1L, "alice", "1234");
        when(userRepository.findByUsername("alice")).thenReturn(Optional.of(user));

        Optional<User> result = userService.login("alice", "1234");
        assertTrue(result.isPresent());
    }

    @Test
    void testLogin_Fail_WrongPassword() {
        User user = new User(1L, "alice", "1234");
        when(userRepository.findByUsername("alice")).thenReturn(Optional.of(user));

        Optional<User> result = userService.login("alice", "wrong");
        assertFalse(result.isPresent());
    }

    @Test
    void testLogin_UserNotFound() {
        when(userRepository.findByUsername("ghost")).thenReturn(Optional.empty());

        Optional<User> result = userService.login("ghost", "pw");
        assertFalse(result.isPresent());
    }

    @Test
    void testGetAllUsers() {
        List<User> users = List.of(new User(1L, "a", "a"), new User(2L, "b", "b"));
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();
        assertEquals(2, result.size());
    }

    @Test
    void testGetUserById() {
        User user = new User(1L, "u", "p");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(1L);
        assertTrue(result.isPresent());
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<User> result = userService.getUserById(99L);
        assertFalse(result.isPresent());
    }
}
