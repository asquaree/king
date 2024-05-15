package com.King.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Map;

public interface WarriorKillCountServiceInterface {

    void ProcessData(Map<String, String> data);

    void updateWarriorKills(Map<String, String> killerCodeKill);

    void saveWarriors(Map<String, String> warriorCodeName) throws JsonProcessingException;

    Map<String, Integer> getTop5Warriors();

}