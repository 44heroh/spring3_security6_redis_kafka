package com.example.spring3security6docker.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ScheduledProducer {
    private final KafkaTemplate<Object, Object> template;

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ScheduledProducer.class);

    public ScheduledProducer(KafkaTemplate<Object, Object> template) {
        this.template = template;
    }

    @Scheduled(fixedDelay = 2000)
    public void sendFoo() {
        log.info("producing message to Kafka, topic=receiving-topic");
        try {
            this.template.send("receiving-topic", Instant.now().toString() + " Hello world!!! ");
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }
}
