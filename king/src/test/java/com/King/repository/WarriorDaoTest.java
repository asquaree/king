package com.King.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WarriorDaoTest {


    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void testWriteData() {
        String key = "test_key";
        String value = "test_value";
        redisTemplate.opsForValue().set(key, value);
        String retrievedValue = redisTemplate.opsForValue().get(key);
        assertEquals(value, retrievedValue);
    }
}