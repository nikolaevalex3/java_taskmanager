package com.example.taskmanager.service.Implementations;

import com.example.taskmanager.event.TaskCreatedEvent;
import com.example.taskmanager.kafka.KafkaProducerService;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import com.example.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final KafkaProducerService kafkaProducerService;

    @Override
    @CacheEvict(value = {"tasksById", "tasksByUser"}, key = "#task.id", allEntries = true)
    public Task createTask(Task task) {
        Task savedTask = taskRepository.save(task);

        TaskCreatedEvent event = new TaskCreatedEvent(
                savedTask.getUserId(),
                "Новая задача: " + savedTask.getTitle(),
                savedTask.getCreatedAt() != null ? savedTask.getCreatedAt() : savedTask.getTargetDate()
        );

        kafkaProducerService.sendTaskCreatedEvent(event);

        return savedTask;
    }

    @Override
    @Cacheable(value = "tasksByUser", key = "#userId")
    public List<Task> getUserTasks(Long userId) {
        return taskRepository.findByUserId(userId);
    }

    @Override
    @Cacheable(value = "pendingTasksByUser", key = "#userId")
    public List<Task> getPendingTasks(Long userId) {
        return taskRepository.findByUserIdAndIsDoneFalse(userId).stream()
                .filter(task -> !task.getIsDeleted())
                .toList();
    }

    @Override
    @CacheEvict(value = {"tasksById", "tasksByUser", "pendingTasksByUser"}, allEntries = true)
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
    @Cacheable(value = "tasksById", key = "#taskId")
    public Optional<Task> getTaskById(Long taskId) {
        return taskRepository.findById(taskId);
    }

    @Override
    public List<Task> findOverdueTasks(LocalDateTime now) {
        return taskRepository.findByTargetDateBeforeAndIsDoneFalseAndIsDeletedFalse(now);
    }
}
