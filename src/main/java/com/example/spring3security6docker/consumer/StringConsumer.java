package com.example.spring3security6docker.consumer;

import com.example.spring3security6docker.dto.request.WeatherCreateRequestDto;
import com.example.spring3security6docker.service.impl.WeatherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class StringConsumer {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(StringConsumer.class);
    private WeatherServiceImpl weatherService;

    @Autowired
    public StringConsumer(WeatherServiceImpl weatherService) {
        this.weatherService = weatherService;
    }

    @KafkaListener(id = "demoGroup", topics = "programmingsharing.topic1")
    public void listen(String message) {
        log.info("programmingsharing.topic1 Received: " + message);
    }

    @KafkaListener(id = "demoGroup2", topics = "receiving-topic")
    public void listenFromReceivingTopic(String message) {
        log.info("receiving-topic Received : " + message);
    }

    @KafkaListener(id = "importWeather", topics = "import-weather-topic")
    public void listenImportWeatherTopic(WeatherCreateRequestDto weatherCreateRequestDto) {
        log.info("import-weather-topic Received : " + weatherCreateRequestDto);
        weatherService.save(weatherCreateRequestDto);
    }
}
