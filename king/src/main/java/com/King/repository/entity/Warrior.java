package com.King.repository.entity;

import org.springframework.boot.context.properties.bind.DefaultValue;
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

    public void setWarriorId(String warriorId) {
        this.warriorId = warriorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
