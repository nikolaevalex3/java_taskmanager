package com.example.taskmanager.service.Implementations;

import com.example.taskmanager.model.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NotificationServiceImplTest {

    private NotificationServiceImpl notificationService;

    @BeforeEach
    void setUp() {
        notificationService = new NotificationServiceImpl();
    }

    @Test
    void testCreateNotification_ShouldAssignIdAndStoreNotification() {
        Notification notification = Notification.builder()
                .userId(1L)
                .message("Test message")
                .isRead(false)
                .build();

        Notification created = notificationService.createNotification(notification);

        assertNotNull(created);
        assertEquals(1L, created.getId());
        assertEquals(1L, created.getUserId());
        assertFalse(created.getIsRead());
        assertEquals("Test message", created.getMessage());
    }

    @Test
    void testGetAllNotificationsForUser_ShouldReturnOnlyUsersNotifications() {
        Notification n1 = Notification.builder()
                .userId(1L)
                .message("User 1 msg")
                .isRead(false)
                .build();
        notificationService.createNotification(n1);

        Notification n2 = Notification.builder()
                .userId(2L)
                .message("User 2 msg")
                .isRead(false)
                .build();
        notificationService.createNotification(n2);

        List<Notification> user1Notifications = notificationService.getAllNotificationsForUser(1L);

        assertEquals(1, user1Notifications.size());
        assertEquals(1L, user1Notifications.get(0).getUserId());
    }

    @Test
    void testGetUnreadNotificationsForUser_ShouldReturnOnlyUnread() {
        Notification n1 = Notification.builder()
                .userId(1L)
                .isRead(false)
                .build();
        notificationService.createNotification(n1);

        Notification n2 = Notification.builder()
                .userId(1L)
                .isRead(true)
                .build();
        notificationService.createNotification(n2);

        List<Notification> unread = notificationService.getUnreadNotificationsForUser(1L);

        assertEquals(1, unread.size());
        assertFalse(unread.get(0).getIsRead());
    }

    @Test
    void testMarkAsRead_ShouldSetNotificationAsRead() {
        Notification notification = Notification.builder()
                .userId(1L)
                .isRead(false)
                .build();

        Notification created = notificationService.createNotification(notification);

        notificationService.markAsRead(created.getId());

        List<Notification> unread = notificationService.getUnreadNotificationsForUser(1L);
        assertTrue(unread.isEmpty());

        List<Notification> all = notificationService.getAllNotificationsForUser(1L);
        assertTrue(all.get(0).getIsRead());
    }

    @Test
    void testMarkAsRead_ShouldDoNothingIfNotificationNotFound() {
        notificationService.markAsRead(999L);  
    }
}
