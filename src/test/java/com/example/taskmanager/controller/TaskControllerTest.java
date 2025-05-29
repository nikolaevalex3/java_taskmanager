package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private Task task;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        task = Task.builder()
                .id(1L)
                .userId(1L)
                .title("Test Task")
                .description("Description")
                .isDone(false)
                .build();
    }

    @Test
    void testCreateTask() {
        when(taskService.createTask(task)).thenReturn(task);

        ResponseEntity<Task> response = taskController.createTask(task);

        assertThat(response.getBody()).isEqualTo(task);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
    }

    @Test
    void testGetUserTasks() {
        when(taskService.getUserTasks(1L)).thenReturn(List.of(task));

        ResponseEntity<List<Task>> response = taskController.getUserTasks(1L);

        assertThat(response.getBody()).containsExactly(task);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
    }

    @Test
    void testGetPendingTasks() {
        when(taskService.getPendingTasks(1L)).thenReturn(List.of(task));

        ResponseEntity<List<Task>> response = taskController.getPendingTasks(1L);

        assertThat(response.getBody()).containsExactly(task);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
    }

    @Test
    void testDeleteTask_Success() {
        when(taskService.deleteTask(1L)).thenReturn(true);

        ResponseEntity<Void> response = taskController.deleteTask(1L);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
    }

    @Test
    void testDeleteTask_NotFound() {
        when(taskService.deleteTask(999L)).thenReturn(false);

        ResponseEntity<Void> response = taskController.deleteTask(999L);

        assertThat(response.getStatusCode().value()).isEqualTo(404);
    }
}
