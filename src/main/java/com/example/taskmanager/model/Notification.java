package com.example.taskmanager.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    
    private Long id;

    private Long userId;

    private String message;

    @Builder.Default
    private LocalDateTime date = LocalDateTime.now();

    @Builder.Default
    private Boolean isRead = false;
}
