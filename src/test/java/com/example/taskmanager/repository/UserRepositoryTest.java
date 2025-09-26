package com.example.taskmanager.repository;

import com.example.taskmanager.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Должен сохранять и находить пользователя по ID")
    void testSaveAndFindById() {
        User user = new User(null, "john_doe", "securepass");
        User savedUser = userRepository.save(user);

        Optional<User> found = userRepository.findById(savedUser.getId());
        assertTrue(found.isPresent());
        assertEquals("john_doe", found.get().getUsername());
    }

    @Test
    @DisplayName("Должен находить пользователя по имени пользователя")
    void testFindByUsername() {
        User user = new User(null, "alice", "alicepass");
        userRepository.save(user);

        Optional<User> found = userRepository.findByUsername("alice");
        assertTrue(found.isPresent());
        assertEquals("alice", found.get().getUsername());
    }

    @Test
    @DisplayName("Должен возвращать пустой Optional, если пользователь не найден")
    void testFindByUsernameNotFound() {
        Optional<User> found = userRepository.findByUsername("nonexistent");
        assertTrue(found.isEmpty());
    }
}
