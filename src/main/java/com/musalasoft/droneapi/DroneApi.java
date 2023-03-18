package com.musalasoft.droneapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DroneApi {
    public static void main(String[] args) {
        SpringApplication.run(DroneApi.class, args);
    }
}
