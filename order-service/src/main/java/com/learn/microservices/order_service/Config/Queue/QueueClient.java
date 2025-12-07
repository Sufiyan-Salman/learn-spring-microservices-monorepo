package com.learn.microservices.order_service.Config.Queue;

public interface QueueClient {
    void send(String topic, String message);
    void send(String topic,String key, String message);
}
