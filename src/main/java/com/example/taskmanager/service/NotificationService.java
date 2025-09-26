package com.example.taskmanager.service;

import com.example.taskmanager.model.Notification;

import java.util.List;

public interface NotificationService {

    Notification saveNotification(Notification notification);

    void saveNotification(Long userId, String message);

    List<Notification> getAllNotificationsForUser(Long userId);

    List<Notification> getUnreadNotificationsForUser(Long userId);

    void markAsRead(Long notificationId);
}
