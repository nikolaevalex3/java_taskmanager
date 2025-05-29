package com.example.taskmanager.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;

    private String username;
    
    private String password;
}
