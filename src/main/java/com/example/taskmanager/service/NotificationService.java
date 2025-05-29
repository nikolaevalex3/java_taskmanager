package com.example.taskmanager.service;

import com.example.taskmanager.model.Notification;

import java.util.List;

public interface NotificationService {

    Notification createNotification(Notification notification);

    List<Notification> getAllNotificationsForUser(Long userId);

    List<Notification> getUnreadNotificationsForUser(Long userId);

    void markAsRead(Long notificationId);
}
