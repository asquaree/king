package com.King.service;

import java.util.Map;

public interface WarriorKillCountServiceInterface {

    void ProcessData(Map<String, String> data);

    void updateWarriorKills(Map<String, String> killerCodeKill);

    void setWarriorCodeName(Map<String, String> warriorCodeName);

    Map<String, Integer> getTop5Warriors();

}