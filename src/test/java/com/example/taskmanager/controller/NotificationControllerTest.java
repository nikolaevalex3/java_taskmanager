package com.example.taskmanager.controller;

import com.example.taskmanager.model.Notification;
import com.example.taskmanager.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationControllerTest {

    private NotificationService notificationService;
    private NotificationController notificationController;

    @BeforeEach
    void setUp() {
        notificationService = mock(NotificationService.class);
        notificationController = new NotificationController(notificationService);
    }

    @Test
    void testGetAllNotifications() {
        Long userId = 1L;
        List<Notification> mockNotifications = List.of(
                Notification.builder().id(1L).userId(userId).message("Test 1").isRead(false).build(),
                Notification.builder().id(2L).userId(userId).message("Test 2").isRead(true).build()
        );

        when(notificationService.getAllNotificationsForUser(userId)).thenReturn(mockNotifications);

        ResponseEntity<List<Notification>> response = notificationController.getAllNotifications(userId);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        verify(notificationService, times(1)).getAllNotificationsForUser(userId);
    }

    @Test
    void testGetUnreadNotifications() {
        Long userId = 1L;
        List<Notification> mockUnread = List.of(
                Notification.builder().id(3L).userId(userId).message("Unread").isRead(false).build()
        );

        when(notificationService.getUnreadNotificationsForUser(userId)).thenReturn(mockUnread);

        ResponseEntity<List<Notification>> response = notificationController.getUnreadNotifications(userId);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
        assertFalse(response.getBody().get(0).getIsRead());
        verify(notificationService, times(1)).getUnreadNotificationsForUser(userId);
    }
}
