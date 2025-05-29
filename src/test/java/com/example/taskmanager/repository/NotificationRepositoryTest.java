package com.example.taskmanager.repository;

import com.example.taskmanager.model.Notification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class NotificationRepositoryTest {

    @Autowired
    private NotificationRepository notificationRepository;

    @Test
    @DisplayName("Должен сохранять и находить уведомления по userId")
    void testFindByUserId() {
        Notification n1 = Notification.builder()
                .userId(1L)
                .message("Message 1")
                .isRead(false)
                .build();

        Notification n2 = Notification.builder()
                .userId(2L)
                .message("Message 2")
                .isRead(true)
                .build();

        notificationRepository.save(n1);
        notificationRepository.save(n2);

        List<Notification> user1Notifications = notificationRepository.findByUserId(1L);
        assertEquals(1, user1Notifications.size());
        assertEquals("Message 1", user1Notifications.get(0).getMessage());
    }

    @Test
    @DisplayName("Должен находить только непрочитанные уведомления")
    void testFindByUserIdAndIsReadFalse() {
        Notification unread = Notification.builder()
                .userId(1L)
                .message("Unread")
                .isRead(false)
                .build();

        Notification read = Notification.builder()
                .userId(1L)
                .message("Read")
                .isRead(true)
                .build();

        notificationRepository.save(unread);
        notificationRepository.save(read);

        List<Notification> unreadList = notificationRepository.findByUserIdAndIsReadFalse(1L);
        assertEquals(1, unreadList.size());
        assertEquals("Unread", unreadList.get(0).getMessage());
        assertFalse(unreadList.get(0).getIsRead());
    }
}
