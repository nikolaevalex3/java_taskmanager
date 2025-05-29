package com.example.taskmanager.service.Implementations;

import com.example.taskmanager.model.User;
import com.example.taskmanager.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl();
    }

    @Test
    void testRegisterUser() {
        User user = userService.registerUser("testuser", "password");
        assertNotNull(user);
        assertEquals("testuser", user.getUsername());
        assertEquals("password", user.getPassword());
        assertNotNull(user.getId());
    }

    @Test
    void testLoginSuccess() {
        userService.registerUser("alex", "1234");
        Optional<User> user = userService.login("alex", "1234");
        assertTrue(user.isPresent());
        assertEquals("alex", user.get().getUsername());
    }

    @Test
    void testLoginFailWithWrongPassword() {
        userService.registerUser("alex", "1234");
        Optional<User> user = userService.login("alex", "wrong");
        assertFalse(user.isPresent());
    }

    @Test
    void testLoginFailWithNonExistentUser() {
        Optional<User> user = userService.login("ghost", "none");
        assertFalse(user.isPresent());
    }

    @Test
    void testGetAllUsers() {
        userService.registerUser("a", "a");
        userService.registerUser("b", "b");
        List<User> users = userService.getAllUsers();
        assertEquals(2, users.size());
    }

    @Test
    void testGetUserById() {
        User user = userService.registerUser("c", "c");
        Optional<User> found = userService.getUserById(user.getId());
        assertTrue(found.isPresent());
        assertEquals("c", found.get().getUsername());
    }

    @Test
    void testGetUserByIdNotFound() {
        Optional<User> found = userService.getUserById(999L);
        assertFalse(found.isPresent());
    }
}
