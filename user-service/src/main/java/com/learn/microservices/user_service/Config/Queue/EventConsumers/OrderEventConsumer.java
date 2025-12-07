package com.learn.microservices.user_service.Config.Queue.EventConsumers;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventConsumer {

    @KafkaListener(topics = "test-topic", groupId = "order-group")
    public void consume(String message) {
        System.out.println("Received message from Kafka: " + message);
    }
}
