package com.King.repository;

import com.King.repository.entity.Warrior;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WarriorDao {

    @Autowired
    private RedisTemplate redisTemplate;

    public static final String HASH_KEY = "Warrior";

    public void saveWarriors(List<Warrior> warriorsList) throws JsonProcessingException {

        for (Warrior warrior : warriorsList) {
            redisTemplate.opsForHash().put(HASH_KEY, warrior.getWarriorId(), warrior);
        }
//        ObjectMapper objectMapper = new ObjectMapper();
//        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(String.valueOf(Warrior.class));
//        redisTemplate.setValueSerializer(serializer);
//        ListOperations<String,String> listOperations= redisTemplate.opsForList();
//
//        for(Warrior warrior:warriorsList){
//            String json = objectMapper.writeValueAsString(warrior);
//            listOperations.rightPush("warriorsList",json);
//        }
    }

    public List<Warrior> findAll() {
        return redisTemplate.opsForHash().values(HASH_KEY);
    }
}
