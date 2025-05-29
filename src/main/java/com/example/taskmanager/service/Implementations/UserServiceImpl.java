package com.example.taskmanager.service.Implementations;

import com.example.taskmanager.model.User;
import com.example.taskmanager.service.UserService;
import org.springframework.stereotype.Service;
import com.example.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User registerUser(String username, String password) {
        User user = User.builder()
                .username(username)
                .password(password)
                .build();
        return userRepository.save(user);
    }

    @Override
    public Optional<User> login(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(user -> user.getPassword().equals(password));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
}
