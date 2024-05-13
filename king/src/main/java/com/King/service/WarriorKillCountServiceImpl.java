package com.King.service;

import com.King.repository.WarriorKillDb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class WarriorKillCountServiceImpl implements WarriorKillCountServiceInterface {

    private final Logger logger = LoggerFactory.getLogger(WarriorKillCountServiceInterface.class);
    @Autowired
    private WarriorKillDb warriorKillDb;


    @Override
    public void ProcessData(Map<String, String> data) {
        Map<String, String> dataKafka = new HashMap<>();
        logger.info("Processing data.... {}", data);
        dataKafka = data;
        logger.info("updating warriors count....");
        updateWarriorKills(dataKafka);

    }

    private Map<String, Integer> top5Scorers = new HashMap<>();

    @Override
    public void updateWarriorKills(Map<String, String> killerCodeKill) {

        String warriorCode = killerCodeKill.entrySet().iterator().next().getKey();
        updateTop5(warriorCode, Integer.parseInt(killerCodeKill.get(warriorCode)));
        logger.info("Top 5 scorers ...... {}", top5Scorers);
    }

    private void updateTop5(String warriorCode, Integer newKill) {

        logger.info("warrior: {} killed: {}", warriorKillDb.getWarriorCodeName().get(warriorCode), newKill);

        Map<String, Integer> warriorCodeKill = warriorKillDb.getWarriorCodeKillCount();

        warriorCodeKill.put(warriorCode, newKill + warriorCodeKill.getOrDefault(warriorCode, 0));
        warriorKillDb.setWarriorCodeKillCount(warriorCodeKill);

        if (top5Scorers.size() >= 5) {
            if (top5Scorers.containsKey(warriorCode)) {
                top5Scorers.put(warriorCode, warriorCodeKill.get(warriorCode));
            } else {
                String lastWarriorCode = top5Scorers.keySet().toArray()[4].toString();
                if (warriorCodeKill.get(lastWarriorCode) < warriorCodeKill.get(warriorCode)) {
                    top5Scorers.remove(lastWarriorCode);
                    top5Scorers.put(warriorCode, warriorCodeKill.get(warriorCode));
                }
            }
        } else {
            top5Scorers.put(warriorCode, warriorCodeKill.get(warriorCode));
        }

        top5Scorers = sortByValueDescending(top5Scorers);
        warriorKillDb.setTop5warriors(top5Scorers);
    }

    @Override
    public void setWarriorCodeName(Map<String, String> warriorCodeName) {
        warriorKillDb.setWarriorCodeName(warriorCodeName);
        logger.info("Registered players - {}", warriorCodeName);
    }

    @Override
    public Map<String, Integer> getTop5Warriors() {
        logger.info("fetching top warriors.....");
        Map<String, Integer> top5WarriorNamesKills = warriorKillDb.getTop5WarriorNameKill();
        if (!warriorKillDb.getTop5warriors().isEmpty()) {
            warriorKillDb.getTop5warriors().forEach((warriorCode, kill) -> top5WarriorNamesKills.put(warriorKillDb.getWarriorCodeName().get(warriorCode), kill));
            sortByValueDescending(top5WarriorNamesKills);
            warriorKillDb.setTop5WarriorNameKill(top5WarriorNamesKills);
        }
        return warriorKillDb.getTop5WarriorNameKill();
    }


    private <K, V extends Comparable<V>> Map<K, V> sortByValueDescending(Map<K, V> map) {
        return map.entrySet().stream().sorted(Map.Entry.<K, V>comparingByValue().reversed()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }


}
