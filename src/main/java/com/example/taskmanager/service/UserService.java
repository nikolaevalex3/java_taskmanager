package com.example.taskmanager.service;

import com.example.taskmanager.model.User;

import java.util.Optional;
import java.util.List;

public interface UserService {

    User registerUser(String username, String password);

    Optional<User> login(String username, String password);

    List<User> getAllUsers();

    Optional<User> getUserById(Long id);
}
