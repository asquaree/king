package com.King.repository;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class WarriorKillDb {


    private Map<String, Integer> newWarriorKill = new HashMap<>();
    private Map<String, String> warriorCodeName = new HashMap<>();
    private Map<String, Integer> top5warriors = new HashMap<>();
    private String tieCaseWarriorCode = "";
    private Map<String, Integer> top5WarriorNameKill = new HashMap<>();
    private Map<String, Integer> warriorCodeKillCount = new HashMap<>();


    public Map<String, Integer> getTop5WarriorNameKill() {
        return top5WarriorNameKill;
    }

    public void setTop5WarriorNameKill(Map<String, Integer> top5WarriorNameKill) {
        this.top5WarriorNameKill = top5WarriorNameKill;
    }


    public Map<String, Integer> getWarriorCodeKillCount() {
        return warriorCodeKillCount;
    }

    public void setWarriorCodeKillCount(Map<String, Integer> warriorCodeKillCount) {
        this.warriorCodeKillCount = warriorCodeKillCount;
    }

    public Map<String, String> getWarriorCodeName() {
        return warriorCodeName;
    }

    public void setWarriorCodeName(Map<String, String> warriorCodeName) {
        this.warriorCodeName = warriorCodeName;
    }


    public Map<String, Integer> getTop5warriors() {
        return top5warriors;
    }

    public void setTop5warriors(Map<String, Integer> top5warriors) {
        this.top5warriors = top5warriors;
    }

    public Map<String, Integer> getNewWarriorKill() {
        return newWarriorKill;
    }

    public void setNewWarriorKill(Map<String, Integer> newWarriorKill) {
        this.newWarriorKill = newWarriorKill;
    }


    public String getTieCaseWarriorCode() {
        return tieCaseWarriorCode;
    }

    public void setTieCaseWarriorCode(String tieCaseWarriorCode) {
        this.tieCaseWarriorCode = tieCaseWarriorCode;
    }
}
