package com.King.repository;

import com.King.repository.entity.Top5Warrior;
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

    public static final String WARRIOR_HASH_KEY = "Warrior";

    public static final String TOP_5_WARRIOR = "Top5";

    public void saveWarriors(List<Warrior> warriorsList) throws JsonProcessingException {

        for (Warrior warrior : warriorsList) {
            redisTemplate.opsForHash().put(WARRIOR_HASH_KEY, warrior.getWarriorId(), warrior);
        }
    }

    public List<Warrior> findAll() {
        return redisTemplate.opsForHash().values(WARRIOR_HASH_KEY);
    }

    public Warrior findWarriorById(String warriorId) {
        return (Warrior) redisTemplate.opsForHash().get(WARRIOR_HASH_KEY, warriorId);
    }

    public Warrior updateWarrior(Warrior warrior) {
        redisTemplate.opsForHash().put(WARRIOR_HASH_KEY, warrior.getWarriorId(), warrior);
        return warrior;
    }

    public Top5Warrior updateTop5Warrior(Top5Warrior warrior) {
        redisTemplate.opsForHash().put(TOP_5_WARRIOR, warrior.getWarriorId(), warrior);
        return warrior;
    }

    public List<Top5Warrior> findTop5() {
        return redisTemplate.opsForHash().values(TOP_5_WARRIOR);
    }

    public Boolean deleteTop5Warrior(String warriorId) {
        redisTemplate.opsForHash().delete(TOP_5_WARRIOR, warriorId);
        return true;
    }

}
