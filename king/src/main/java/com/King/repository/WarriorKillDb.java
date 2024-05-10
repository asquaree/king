package com.King.repository;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Repository
public class WarriorKillDb {


    public Map<String, Integer> getNewWarriorKill() {
        return newWarriorKill;
    }

    public void setNewWarriorKill(Map<String, Integer> newWarriorKill) {
        this.newWarriorKill = newWarriorKill;
    }

    private Map<String, Integer> newWarriorKill= new HashMap<>();
    private Map<String,String> warriorCodeName = new HashMap<>();

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

    private Map<String,Integer> warriorCodeKillCount = new HashMap<>();

    public TreeMap<String,Integer> getTop5warriors() {
        return top5warriors;
    }

    public void setTop5warriors(TreeMap<String, Integer> top5warriors) {
        this.top5warriors = top5warriors;
    }

    private TreeMap<String,Integer> top5warriors = new TreeMap<>();

    private String tieCaseWarriorCode ="";

    public String getTieCaseWarriorCode() {
        return tieCaseWarriorCode;
    }

    public void setTieCaseWarriorCode(String tieCaseWarriorCode) {
        this.tieCaseWarriorCode = tieCaseWarriorCode;
    }
}
