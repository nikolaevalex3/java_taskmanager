package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskService {

    Task createTask(Task task);

    List<Task> getUserTasks(Long userId);

    List<Task> getPendingTasks(Long userId);

    boolean deleteTask(Long taskId);

    Optional<Task> getTaskById(Long taskId);

    List<Task> findOverdueTasks(LocalDateTime now);
}
