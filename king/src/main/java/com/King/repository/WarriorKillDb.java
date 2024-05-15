package com.King.repository;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class WarriorKillDb {


    private Map<String, Integer> top5warriors = new HashMap<>();
    private Map<String, Integer> top5WarriorNameKill = new HashMap<>();


    public Map<String, Integer> getTop5WarriorNameKill() {
        return top5WarriorNameKill;
    }

    public void setTop5WarriorNameKill(Map<String, Integer> top5WarriorNameKill) {
        this.top5WarriorNameKill = top5WarriorNameKill;
    }


    public Map<String, Integer> getTop5warriors() {
        return top5warriors;
    }

    public void setTop5warriors(Map<String, Integer> top5warriors) {
        this.top5warriors = top5warriors;
    }


}


