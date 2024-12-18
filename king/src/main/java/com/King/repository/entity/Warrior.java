package com.King.repository.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("Warrior")
public class Warrior implements Serializable {

    @Id
    private String warriorId;
    private String name;
    private Integer score= 0;

    public Warrior(String warriorId, String name) {
        this.warriorId = warriorId;
        this.name = name;
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

    public void setScore(Integer score) {
        this.score = score;
    }
}
