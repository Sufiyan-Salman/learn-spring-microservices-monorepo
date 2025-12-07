package com.learn.microservices.user_service.Config.Queue;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component("kafkaQueueClient")
//@ConditionalOnProperty(name = "queue.client.type", havingValue = "kafka")
@RequiredArgsConstructor
@Slf4j
public class KafkaQueueClient implements QueueClient {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void send(String topic, String message) {
        kafkaTemplate.send(topic, message)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("[Kafka] Sent to {}: {}", topic, message);
                    } else {
                        log.error("[Kafka] Failed sending to {}: {}", topic, message, ex);
                    }
                });
    }
    @Override
    public void send(String topic, String key, String message) {
        kafkaTemplate.send(topic, key, message)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("[Kafka] Sent to {}: {}", topic, message);
                    } else {
                        log.error("[Kafka] Failed sending to {}: {}", topic, message, ex);
                    }
                });
    }
}
