package com.example.taskmanager.model;

import lombok.*;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasks")
public class Task implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String title;

    private String description;

    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime targetDate;

    @Builder.Default
    @Column(nullable = false)
    private Boolean isDone = false;

    @Builder.Default
    @Column(nullable = false)
    private Boolean isDeleted = false;
}
