package com.example.taskmanager.service.Implementations;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTask() {
        Task input = Task.builder().userId(1L).title("Test").build();
        Task saved = Task.builder().id(1L).userId(1L).title("Test").build();

        when(taskRepository.save(input)).thenReturn(saved);

        Task result = taskService.createTask(input);
        assertEquals(saved, result);
    }

    @Test
    void testGetUserTasks() {
        List<Task> tasks = List.of(Task.builder().userId(1L).title("T").build());
        when(taskRepository.findByUserId(1L)).thenReturn(tasks);

        List<Task> result = taskService.getUserTasks(1L);
        assertEquals(1, result.size());
    }

    @Test
    void testGetPendingTasks() {
        Task task1 = Task.builder()
            .userId(1L)
            .title("Pending Task")
            .isDone(false)
            .isDeleted(false)
            .build();

        Task task2 = Task.builder()
            .userId(1L)
            .title("Deleted Task")
            .isDone(false)
            .isDeleted(true) // этот должен быть отфильтрован
            .build();

        List<Task> tasks = List.of(task1, task2);

        when(taskRepository.findByUserIdAndIsDoneFalse(1L)).thenReturn(tasks);

        List<Task> result = taskService.getPendingTasks(1L);

        assertEquals(1, result.size());
        assertEquals("Pending Task", result.get(0).getTitle());
    }

    @Test
    void testDeleteTask_Success() {
        Task task = Task.builder().id(1L).userId(1L).isDeleted(false).build();
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        boolean result = taskService.deleteTask(1L);
        assertTrue(result);
        assertTrue(task.getIsDeleted());
        verify(taskRepository).save(task);
    }

    @Test
    void testDeleteTask_AlreadyDeleted() {
        Task task = Task.builder().id(1L).userId(1L).isDeleted(true).build();
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        boolean result = taskService.deleteTask(1L);
        assertFalse(result);
        verify(taskRepository, never()).save(any());
    }

    @Test
    void testGetTaskById() {
        Task task = Task.builder().id(1L).title("Found").build();
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Optional<Task> result = taskService.getTaskById(1L);
        assertTrue(result.isPresent());
        assertEquals("Found", result.get().getTitle());
    }

    @Test
    void testGetTaskById_NotFound() {
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());
        Optional<Task> result = taskService.getTaskById(999L);
        assertFalse(result.isPresent());
    }
}
