package com.example.taskmanager.service.Implementations;

import com.example.taskmanager.model.Notification;
import com.example.taskmanager.repository.NotificationRepository;
import com.example.taskmanager.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public List<Notification> getAllNotificationsForUser(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    @Override
    public List<Notification> getUnreadNotificationsForUser(Long userId) {
        return notificationRepository.findByUserIdAndIsReadFalse(userId);
    }

    @Override
    public void markAsRead(Long notificationId) {
        Optional<Notification> optional = notificationRepository.findById(notificationId);
        if (optional.isPresent()) {
            Notification notification = optional.get();
            notification.setIsRead(true);
            notificationRepository.save(notification);
        }
    }

    public Notification saveNotification(Notification notification) {
    return notificationRepository.save(notification);
    }

    @Async
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
