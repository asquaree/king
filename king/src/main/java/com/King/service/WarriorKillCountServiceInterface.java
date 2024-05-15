package com.King.service;

import com.King.repository.entity.Top5Warrior;
import com.King.repository.entity.Warrior;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.catalina.LifecycleState;

import java.util.List;
import java.util.Map;

public interface WarriorKillCountServiceInterface {

    void ProcessData(Map<String, String> data);

    void updateWarriorKills(Map<String, String> killerCodeKill);

    void saveWarriors(Map<String, String> warriorCodeName) throws JsonProcessingException;

    List<Warrior> getRegisteredWarriors();

    List<Top5Warrior> getTop5Warriors();

}