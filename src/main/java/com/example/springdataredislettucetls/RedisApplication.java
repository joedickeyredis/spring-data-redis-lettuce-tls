package com.example.springdataredislettucetls;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class RedisApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(RedisApplication.class, args);

        RedisService redisService = context.getBean(RedisService.class);

        // Hardcoded examples
        String key = "myKey";
        String value = "myValue";

        // Set a key-value pair
        redisService.setValue(key, value);

        // Get the value of a key
        String retrievedValue = redisService.getValue(key);
        System.out.println("Retrieved Value: " + retrievedValue);

        // Clean up: Delete the key
        redisService.deleteKey(key);

    }

}
