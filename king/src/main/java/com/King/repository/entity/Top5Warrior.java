package com.King.repository.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("Top5Warrior")
public class Top5Warrior implements Serializable {

    @Id
    private String warriorId;
    private String name;
    private Integer score = 0;

    public Top5Warrior(String warriorId, String name, Integer score) {
        this.warriorId = warriorId;
        this.name = name;
        this.score = score;
    }

    public String getWarriorId() {
        return warriorId;
    }


    public String getName() {
        return name;
    }

    public Integer getScore() {
        return score;
    }

}
