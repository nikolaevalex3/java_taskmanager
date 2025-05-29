package com.example.taskmanager.service.Implementations;

import com.example.taskmanager.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceImplTest {

    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        taskService = new TaskServiceImpl();
    }

    @Test
    void testCreateTask() {
        Task task = new Task();
        task.setUserId(1L);
        task.setTitle("Test Task");

        Task created = taskService.createTask(task);

        assertNotNull(created.getId());
        assertEquals("Test Task", created.getTitle());
        assertEquals(1L, created.getUserId());
    }

    @Test
    void testGetUserTasks() {
        Task task1 = new Task();
        task1.setUserId(1L);
        task1.setTitle("Task 1");
        taskService.createTask(task1);

        Task task2 = new Task();
        task2.setUserId(2L);
        task2.setTitle("Task 2");
        taskService.createTask(task2);

        List<Task> userTasks = taskService.getUserTasks(1L);
        assertEquals(1, userTasks.size());
        assertEquals("Task 1", userTasks.get(0).getTitle());
    }

    @Test
    void testGetPendingTasks() {
        Task task1 = new Task();
        task1.setUserId(1L);
        task1.setTitle("Pending Task");
        task1.setIsDone(false);
        task1.setIsDeleted(false);
        taskService.createTask(task1);

        Task task2 = new Task();
        task2.setUserId(1L);
        task2.setTitle("Done Task");
        task2.setIsDone(true);
        task2.setIsDeleted(false);
        taskService.createTask(task2);

        Task task3 = new Task();
        task3.setUserId(1L);
        task3.setTitle("Deleted Task");
        task3.setIsDone(false);
        task3.setIsDeleted(true);
        taskService.createTask(task3);

        List<Task> pending = taskService.getPendingTasks(1L);
        assertEquals(1, pending.size());
        assertEquals("Pending Task", pending.get(0).getTitle());
    }

    @Test
    void testDeleteTask() {
        Task task = new Task();
        task.setUserId(1L);
        task.setIsDone(false);
        task.setIsDeleted(false);
        task = taskService.createTask(task);

        boolean deleted = taskService.deleteTask(task.getId());
        assertTrue(deleted);

        Optional<Task> fetched = taskService.getTaskById(task.getId());
        assertTrue(fetched.isPresent());
        assertTrue(fetched.get().getIsDeleted());
    }

    @Test
    void testDeleteAlreadyDeletedTask() {
        Task task = new Task();
        task.setUserId(1L);
        task.setIsDone(false);
        task.setIsDeleted(true);
        task = taskService.createTask(task);

        boolean deleted = taskService.deleteTask(task.getId());
        assertFalse(deleted);
    }

    @Test
    void testGetTaskById() {
        Task task = new Task();
        task.setUserId(1L);
        task.setTitle("Lookup Task");
        task = taskService.createTask(task);

        Optional<Task> found = taskService.getTaskById(task.getId());
        assertTrue(found.isPresent());
        assertEquals("Lookup Task", found.get().getTitle());
    }

    @Test
    void testGetTaskById_NotFound() {
        Optional<Task> found = taskService.getTaskById(999L);
        assertFalse(found.isPresent());
    }
}
