package com.example.taskmanager.service.Implementations;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import org.springframework.stereotype.Service;
import com.example.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public List<Task> getUserTasks(Long userId) {
    return taskRepository.findByUserId(userId);
}

    @Override
    public List<Task> getPendingTasks(Long userId) {
        return taskRepository.findByUserIdAndIsDoneFalse(userId).stream()
                .filter(task -> !task.getIsDeleted())
                .toList();
    }

    @Override
    public boolean deleteTask(Long taskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            if (!task.getIsDeleted()) {
                task.setIsDeleted(true);
                taskRepository.save(task);
                return true;
            }
        }
        return false;
    }

    @Override
    public Optional<Task> getTaskById(Long taskId) {
        return taskRepository.findById(taskId);
    }
}
