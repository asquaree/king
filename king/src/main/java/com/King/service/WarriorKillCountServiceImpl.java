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

    private Map<String, String> dataKafka = new HashMap<>();

    @Override
    public void ProcessData(Map<String, String> data) {
        logger.info("Processing data.... {}", data);
        dataKafka = data;
        logger.info("updating warriors count....");
        updateWarriorKills(dataKafka);

    }

    private TreeMap<String, Integer> top5Scorers = new TreeMap<>();

    public TreeMap<String, Integer> top5killer() {

        return warriorKillDb.getTop5warriors();
    }

    public Boolean updateWarriorKills(Map<String, String> killerCodeKill) {


        String warriorCode = killerCodeKill.entrySet().iterator().next().getKey();
        updateTop5(warriorCode, Integer.parseInt(killerCodeKill.get(warriorCode)));
        return Boolean.TRUE;
    }


    private void updateTop5(String warriorCode, Integer newKill) {

        logger.info("updating top5 scorers... warriorCode: {}, kill: {}", warriorCode, newKill);

        Map<String, Integer> warriorCodeKill = warriorKillDb.getWarriorCodeKillCount();

        warriorCodeKill.put(warriorCode, newKill + warriorCodeKill.getOrDefault(warriorCode, 0));
        warriorKillDb.setWarriorCodeKillCount(warriorCodeKill);
        if (warriorKillDb.getTop5warriors().isEmpty()) {
            top5Scorers.put(warriorCode, newKill);
            warriorKillDb.setTop5warriors(top5Scorers);
            return;
        }
        List<Map.Entry<String, Integer>> sortedTop5Entries = top5Scorers.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue, Collections.reverseOrder()))
                .collect(Collectors.toList());

        if (top5Scorers.size() < 5) {
            top5Scorers.put(warriorCode, warriorCodeKill.get(warriorCode));
        }
        else if (warriorCodeKill.get(warriorCode) > sortedTop5Entries.get(4).getValue() || warriorCodeKill.get(warriorCode).equals(sortedTop5Entries.get(4).getValue())) {
            //sortedTop5Entries.put(warriorCode, warriorCodeKill.get(warriorCode));
            if (top5Scorers.size() >= 5) {
                warriorKillDb.setTieCaseWarriorCode(top5Scorers.lastEntry().getKey());
                top5Scorers.remove(top5Scorers.lastEntry().getKey());
            }
        }

        warriorKillDb.setTop5warriors(top5Scorers);
    }

    public Boolean setWarriorCodeName(Map<String, String> warriorCodeName) {
        warriorKillDb.setWarriorCodeName(warriorCodeName);
        return true;
    }

}
