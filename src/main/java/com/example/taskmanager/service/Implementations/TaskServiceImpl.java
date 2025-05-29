package com.example.taskmanager.service.Implementations;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskServiceImpl implements TaskService {

    private final Map<Long, Task> tasks = new HashMap<>();
    private long currentId = 1L;

    @Override
    public Task createTask(Task task) {
        task.setId(currentId++);
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public List<Task> getUserTasks(Long userId) {
    return tasks.values().stream()
            .filter(task -> userId.equals(task.getUserId()))
            .toList();
}

    @Override
    public List<Task> getPendingTasks(Long userId) {
        return tasks.values().stream()
                .filter(t -> t.getUserId().equals(userId) && !t.getIsDone() && !t.getIsDeleted())
                .toList();
    }

    @Override
    public boolean deleteTask(Long taskId) {
        Task task = tasks.get(taskId);
        if (task != null && !task.getIsDeleted()) {
            task.setIsDeleted(true);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Task> getTaskById(Long taskId) {
        return Optional.ofNullable(tasks.get(taskId));
    }
}
