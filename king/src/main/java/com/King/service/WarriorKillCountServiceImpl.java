package com.King.service;

import com.King.repository.WarriorDao;
import com.King.repository.WarriorKillDb;
import com.King.repository.WarriorMapper;
import com.King.repository.entity.Warrior;
import com.fasterxml.jackson.core.JsonProcessingException;
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

    @Autowired
    private WarriorMapper warriorMapper;

    @Autowired
    private WarriorDao warriorDao;

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

        Warrior warrior= warriorDao.findWarriorById(warriorCode);
        logger.info("warrior: {} killed: {}", warrior.getName(), newKill);

        warrior.setScore(warrior.getScore() + newKill);
        warriorDao.updateWarrior(warrior);

        if (top5Scorers.size() >= 5) {
            if (top5Scorers.containsKey(warrior.getWarriorId())) {
                top5Scorers.put(warrior.getWarriorId(), warrior.getScore());
            } else {
                String lastWarriorCode = top5Scorers.keySet().toArray()[4].toString();
                if (top5Scorers.get(lastWarriorCode) < warrior.getScore()) {
                    top5Scorers.remove(lastWarriorCode);
                    top5Scorers.put(warrior.getWarriorId(), warrior.getScore());
                }
            }
        } else {
            top5Scorers.put(warrior.getWarriorId(), warrior.getScore());
        }

        top5Scorers = sortByValueDescending(top5Scorers);
        warriorKillDb.setTop5warriors(top5Scorers);
    }

    @Override
    public void saveWarriors(Map<String, String> warriorCodeName) throws JsonProcessingException {

        List<Warrior> warriorsList = warriorMapper.mapWarriorMapToEntityList(warriorCodeName);
        warriorDao.saveWarriors(warriorsList);
        logger.info("Registered players - {}", warriorCodeName);
    }

    public List<Warrior> getRegisteredWarriors() {

        return warriorDao.findAll();
    }

    @Override
    public Map<String, Integer> getTop5Warriors() {
        logger.info("fetching top warriors.....");
        Map<String, Integer> top5WarriorNamesKills = warriorKillDb.getTop5WarriorNameKill();
        if (!warriorKillDb.getTop5warriors().isEmpty()) {
            warriorKillDb.getTop5warriors().forEach((warriorCode, kill) -> top5WarriorNamesKills.put(warriorDao.findWarriorById(warriorCode).getName(), kill));
            sortByValueDescending(top5WarriorNamesKills);
            warriorKillDb.setTop5WarriorNameKill(top5WarriorNamesKills);
        }
        return warriorKillDb.getTop5WarriorNameKill();
    }


    private <K, V extends Comparable<V>> Map<K, V> sortByValueDescending(Map<K, V> map) {
        return map.entrySet().stream().sorted(Map.Entry.<K, V>comparingByValue().reversed()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }


}
