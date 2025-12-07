package com.learn.microservices.order_service.Config.Queue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class QueueClientFactory {

    private final Map<String, QueueClient> clientMap = new HashMap<>();

    public QueueClientFactory(List<QueueClient> clients) {
        for (QueueClient client : clients) {
            String key = client.getClass().getSimpleName(); // e.g. KafkaQueueClient
            clientMap.put(key, client);
            log.info("[Factory] Registered QueueClient: {}", key);
        }
    }

    public QueueClient get(String clientName) {
        QueueClient client = clientMap.get(clientName);
        if (client == null) {
            throw new IllegalArgumentException("No QueueClient registered for: " + clientName);
        }
        return client;
    }
}
