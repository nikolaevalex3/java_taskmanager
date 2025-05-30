package com.example.taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class TaskManageApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskManageApplication.class, args);
	}

}
