package com.example.taskmanager.service.Implementations;

import com.example.taskmanager.model.Notification;
import com.example.taskmanager.service.NotificationService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Profile("inmemory")
public class NotificationServiceImpl implements NotificationService {

    private final Map<Long, Notification> notifications = new HashMap<>();
    private long currentId = 1L;

    @Override
    public Notification saveNotification(Notification notification) {
        notification.setId(currentId++);
        notifications.put(notification.getId(), notification);
        return notification;
    }

    @Override
    public List<Notification> getAllNotificationsForUser(Long userId) {
        return notifications.values().stream()
                .filter(n -> n.getUserId().equals(userId))
                .toList();
    }

    @Override
    public List<Notification> getUnreadNotificationsForUser(Long userId) {
        return notifications.values().stream()
                .filter(n -> n.getUserId().equals(userId) && !n.getIsRead())
                .toList();
    }

    @Override
    public void markAsRead(Long notificationId) {
        Notification notification = notifications.get(notificationId);
        if (notification != null) {
            notification.setIsRead(true);
        }
    }


    @Override
    public void saveNotification(Long userId, String message) {
    Notification notification = Notification.builder()
            .userId(userId)
            .message(message)
            .date(LocalDateTime.now())
            .isRead(false)
            .build();
            saveNotification(notification);
}
}