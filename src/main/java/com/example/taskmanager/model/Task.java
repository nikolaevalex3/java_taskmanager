package com.example.taskmanager.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    private Long id;

    private Long userId;

    private String title;

    private String description;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime targetDate;

    @Builder.Default
    private Boolean isDone = false;

    @Builder.Default
    private Boolean isDeleted = false;
}
