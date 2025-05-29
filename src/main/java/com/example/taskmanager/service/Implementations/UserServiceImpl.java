package com.example.taskmanager.service.Implementations;

import com.example.taskmanager.model.User;
import com.example.taskmanager.service.UserService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final Map<Long, User> users = new HashMap<>();
    private long currentId = 1L;

    @Override
    public User registerUser(String username, String password) {
        User user = User.builder()
                .id(currentId++)
                .username(username)
                .password(password)
                .build();
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> login(String username, String password) {
        return users.values().stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst();
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return Optional.ofNullable(users.get(id));
    }
}
