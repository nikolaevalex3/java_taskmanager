package com.example.taskmanager.scheduler;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import com.example.taskmanager.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OverdueTaskChecker {

    private final TaskService taskService;
    private final NotificationService notificationService; 

    @Async
    @Scheduled(fixedRate = 60000) // 60 секунд
    public void checkOverdueTasks() {
        List<Task> overdueTasks = taskService.findOverdueTasks(LocalDateTime.now());
        for (Task task : overdueTasks) {
            log.info("Обнаружена просроченная задача: {}", task.getTitle());
            notificationService.saveNotification(task.getUserId(), "Task overdue: " + task.getTitle());
        }
    }
}
