package com.example.springdataredislettucetls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final Logger logger = LoggerFactory.getLogger(RedisService.class);

    @Autowired
    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setValue(String key, String value) {
        logger.info("Writing key '{}' with value '{}'", key, value);
        redisTemplate.opsForValue().set(key, value);
    }

    public String getValue(String key) {
        logger.info("Reading key '{}'", key);
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void deleteKey(String key) {
        logger.info("Deleting key '{}'", key);
        redisTemplate.delete(key);
    }
}
