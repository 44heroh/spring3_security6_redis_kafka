package com.example.spring3security6docker.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class StringConsumer {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(StringConsumer.class);

    @KafkaListener(id = "demoGroup", topics = "programmingsharing.topic1")
    public void listen(String message) {
        log.info("programmingsharing.topic1 Received: " + message);
    }

    @KafkaListener(id = "demoGroup2", topics = "receiving-topic")
    public void listenFromReceivingTopic(String message) {
        log.info("receiving-topic Received : " + message);
    }
}
