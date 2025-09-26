package com.example.taskmanager.service.Implementations;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import java.util.Map;
import java.util.HashMap;

import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Profile;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Optional;

@Service
@Profile("inmemory")
public class TaskServiceImpl implements TaskService {

    private final Map<Long, Task> tasks = new HashMap<>();
    private long currentId = 1L;

    @Override
    @CacheEvict(value = {"tasksById", "tasksByUser"}, key = "#task.id", allEntries = true)
    public Task createTask(Task task) {
        task.setId(currentId++);
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    @Cacheable(value = "tasksByUser", key = "#userId")
    public List<Task> getUserTasks(Long userId) {
    return tasks.values().stream()
            .filter(task -> userId.equals(task.getUserId()))
            .toList();
    }


    @Override
    @Cacheable(value = "pendingTasksByUser", key = "#userId")
    public List<Task> getPendingTasks(Long userId) {
        return tasks.values().stream()
                .filter(t -> t.getUserId().equals(userId) && !t.getIsDone() && !t.getIsDeleted())
                .toList();
    }

    @Override
    @CacheEvict(value = {"tasksById", "tasksByUser", "pendingTasksByUser"}, allEntries = true)
    public boolean deleteTask(Long taskId) {
        Task task = tasks.get(taskId);
        if (task != null && !task.getIsDeleted()) {
            task.setIsDeleted(true);
            return true;
        }
        return false;
    }

    @Override
    @Cacheable(value = "tasksById", key = "#taskId")
    public Optional<Task> getTaskById(Long taskId) {
        return Optional.ofNullable(tasks.get(taskId));
    }
}