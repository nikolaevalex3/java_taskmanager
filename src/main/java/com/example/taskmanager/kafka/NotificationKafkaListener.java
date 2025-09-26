package com.example.taskmanager.kafka;

import com.example.taskmanager.event.TaskCreatedEvent;
import com.example.taskmanager.model.Notification;
import com.example.taskmanager.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class NotificationKafkaListener {

    private final NotificationService notificationService;

    @KafkaListener(topics = "task-created", groupId = "notification_group")
    public void listenTaskCreated(TaskCreatedEvent event) {
        System.out.println("Получено событие из Kafka: " + event);
        Notification notification = Notification.builder()
                .userId(event.getUserId())
                .message("New task created: " + event.getMessage())
                .date(LocalDateTime.now())
                .isRead(false)
                .build();

        notificationService.saveNotification(notification);
    }
}
