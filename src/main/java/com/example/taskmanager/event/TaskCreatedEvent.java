package com.example.taskmanager.event;

import java.time.LocalDateTime;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskCreatedEvent {
    private Long userId;
    private String message;
    private LocalDateTime date;
}
