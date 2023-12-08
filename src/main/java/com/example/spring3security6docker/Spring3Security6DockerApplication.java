package com.example.spring3security6docker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

//@EnableCaching
@SpringBootApplication
@EnableScheduling
public class Spring3Security6DockerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Spring3Security6DockerApplication.class, args);
    }

}
