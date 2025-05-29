package com.example.taskmanager.controller;

import com.example.taskmanager.model.User;
import com.example.taskmanager.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_ShouldReturnCreatedUser() {
        // Arrange
        User inputUser = new User();
        inputUser.setUsername("john");
        inputUser.setPassword("pass123");

        User createdUser = new User();
        createdUser.setUsername("john");
        createdUser.setPassword("pass123");
        createdUser.setId(1L);

        when(userService.registerUser("john", "pass123")).thenReturn(createdUser);

        // Act
        ResponseEntity<User> response = userController.register(inputUser);

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(createdUser);
        verify(userService, times(1)).registerUser("john", "pass123");
    }

    @Test
    void login_WhenUserFound_ShouldReturnUser() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setUsername("john");
        user.setPassword("pass123");

        when(userService.login("john", "pass123")).thenReturn(Optional.of(user));

        // Act
        ResponseEntity<User> response = userController.login("john", "pass123");

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(user);
        verify(userService, times(1)).login("john", "pass123");
    }

    @Test
    void login_WhenUserNotFound_ShouldReturn401() {
        // Arrange
        when(userService.login("john", "wrongpass")).thenReturn(Optional.empty());

        // Act
        ResponseEntity<User> response = userController.login("john", "wrongpass");

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(401);
        assertThat(response.getBody()).isNull();
        verify(userService, times(1)).login("john", "wrongpass");
    }
}
