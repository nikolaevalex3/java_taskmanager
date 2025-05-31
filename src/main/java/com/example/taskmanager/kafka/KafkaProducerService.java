package com.example.taskmanager.kafka;

import com.example.taskmanager.event.TaskCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, TaskCreatedEvent> kafkaTemplate;
    

    public void sendTaskCreatedEvent(TaskCreatedEvent event) {
        kafkaTemplate.send("task-created", event);
    }
}
