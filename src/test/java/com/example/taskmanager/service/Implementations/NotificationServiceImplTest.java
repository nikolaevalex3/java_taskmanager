package com.example.taskmanager.service.Implementations;

import com.example.taskmanager.model.Notification;
import com.example.taskmanager.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationServiceImplTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateNotification() {
        Notification notification = Notification.builder().userId(1L).message("Hi").isRead(false).build();
        Notification saved = Notification.builder().id(1L).userId(1L).message("Hi").isRead(false).build();

        when(notificationRepository.save(notification)).thenReturn(saved);

        Notification result = notificationService.createNotification(notification);
        assertEquals(saved, result);
    }

    @Test
    void testGetAllNotificationsForUser() {
        List<Notification> notifications = List.of(
            Notification.builder().id(1L).userId(1L).message("A").isRead(false).build(),
            Notification.builder().id(2L).userId(1L).message("B").isRead(true).build()
        );
        when(notificationRepository.findByUserId(1L)).thenReturn(notifications);

        List<Notification> result = notificationService.getAllNotificationsForUser(1L);
        assertEquals(2, result.size());
    }

    @Test
    void testGetUnreadNotificationsForUser() {
        List<Notification> unread = List.of(Notification.builder().id(3L).userId(1L).message("X").isRead(false).build());
        when(notificationRepository.findByUserIdAndIsReadFalse(1L)).thenReturn(unread);

        List<Notification> result = notificationService.getUnreadNotificationsForUser(1L);
        assertEquals(1, result.size());
        assertFalse(result.get(0).getIsRead());
    }

    @Test
    void testMarkAsRead() {
        Notification unread = Notification.builder()
            .id(1L)
            .userId(1L)
            .message("Msg")
            .isRead(false)
            .build();
        when(notificationRepository.findById(1L)).thenReturn(Optional.of(unread));

        notificationService.markAsRead(1L);
        assertTrue(unread.getIsRead());
        verify(notificationRepository).save(unread);
    }

    @Test
    void testMarkAsRead_NotFound() {
        when(notificationRepository.findById(999L)).thenReturn(Optional.empty());
        notificationService.markAsRead(999L);  // should not throw
    }
}
