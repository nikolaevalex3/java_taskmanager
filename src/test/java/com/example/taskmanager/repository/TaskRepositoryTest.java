package com.example.taskmanager.repository;

import com.example.taskmanager.model.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    @DisplayName("Должен находить задачи по userId")
    void testFindByUserId() {
        Task task1 = Task.builder()
                .userId(1L)
                .title("User 1 Task")
                .targetDate(LocalDateTime.now().plusDays(1))
                .isDone(false)
                .isDeleted(false)
                .build();

        Task task2 = Task.builder()
                .userId(2L)
                .title("User 2 Task")
                .targetDate(LocalDateTime.now().plusDays(2))
                .isDone(false)
                .isDeleted(false)
                .build();

        taskRepository.save(task1);
        taskRepository.save(task2);

        List<Task> user1Tasks = taskRepository.findByUserId(1L);
        assertEquals(1, user1Tasks.size());
        assertEquals("User 1 Task", user1Tasks.get(0).getTitle());
    }

    @Test
    @DisplayName("Должен находить незавершённые задачи по userId")
    void testFindByUserIdAndIsDoneFalse() {
        Task pending = Task.builder()
                .userId(1L)
                .title("Pending Task")
                .targetDate(LocalDateTime.now().plusDays(1))
                .isDone(false)
                .isDeleted(false)
                .build();

        Task done = Task.builder()
                .userId(1L)
                .title("Done Task")
                .targetDate(LocalDateTime.now().plusDays(2))
                .isDone(true)
                .isDeleted(false)
                .build();

        taskRepository.save(pending);
        taskRepository.save(done);

        List<Task> pendingTasks = taskRepository.findByUserIdAndIsDoneFalse(1L);
        assertEquals(1, pendingTasks.size());
        assertEquals("Pending Task", pendingTasks.get(0).getTitle());
        assertFalse(pendingTasks.get(0).getIsDone());
    }
}
