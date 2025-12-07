package com.learn.microservices.user_service.Config.Queue.EventProducers;

import com.learn.microservices.user_service.Config.Queue.QueueClientFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventProducer {

    private final QueueClientFactory queueClientFactory;

    public void sendOrderEvent(String topic, String eventJson) {
        // Example: can be read from config or hardcoded as per the need
        String selectedClient = "KafkaQueueClient";

        queueClientFactory.get(selectedClient)
                .send(topic, eventJson);
    }
    public void sendOrderEvent(String topic, String key, String eventJson) {
        // Example: can be read from config or hardcoded as per the need
        String selectedClient = "KafkaQueueClient";

        queueClientFactory.get(selectedClient)
                .send(topic, key, eventJson);
    }
}
