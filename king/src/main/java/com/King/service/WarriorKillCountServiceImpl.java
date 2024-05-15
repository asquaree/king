package com.King.service;

import com.King.repository.WarriorDao;

import com.King.repository.WarriorMapper;
import com.King.repository.WarriorTopWarriorMapper;
import com.King.repository.entity.Top5Warrior;
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
    private WarriorMapper warriorMapper;

    @Autowired
    private WarriorTopWarriorMapper warriorTopWarriorMapper;

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

    @Override
    public void updateWarriorKills(Map<String, String> killerCodeKill) {

        String warriorCode = killerCodeKill.entrySet().iterator().next().getKey();
        updateTop5(warriorCode, Integer.parseInt(killerCodeKill.get(warriorCode)));
        Map<String, Integer> top5WarriorsMap = new HashMap<>();
        List<Top5Warrior> top5Warriors = warriorDao.findTop5();
        top5Warriors.forEach(w -> top5WarriorsMap.put(w.getName(), w.getScore()));
        logger.info("Top 5 scorers ...... {}", sortByValueDescending(top5WarriorsMap));
    }

    private void updateTop5(String warriorCode, Integer newKill) {

        Warrior warrior = warriorDao.findWarriorById(warriorCode);
        List<Top5Warrior> top5Warriors = warriorDao.findTop5();
        top5Warriors.sort((w1, w2) -> Integer.compare(w2.getScore(), w1.getScore()));
        logger.info("warrior: {} killed: {}", warrior.getName(), newKill);

        warrior.setScore(warrior.getScore() + newKill);
        warriorDao.updateWarrior(warrior);

        if (top5Warriors.size() >= 5) {

            if (top5Warriors.stream().filter(w -> Objects.equals(w.getWarriorId(), warrior.getWarriorId())).findFirst().orElse(null) != null) {
                warriorDao.updateTop5Warrior(warriorTopWarriorMapper.top5WarriorMapper(warrior));
            } else if (top5Warriors.get(4).getScore() < warrior.getScore()) {
                warriorDao.deleteTop5Warrior(top5Warriors.get(4).getWarriorId());
                warriorDao.updateTop5Warrior(warriorTopWarriorMapper.top5WarriorMapper(warrior));
            }
        } else {
            warriorDao.updateTop5Warrior(warriorTopWarriorMapper.top5WarriorMapper(warrior));
        }
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
    public List<Top5Warrior> getTop5Warriors() {
        logger.info("fetching top warriors.....");
        List<Top5Warrior> top5Warriors = warriorDao.findTop5();
        top5Warriors.sort((w1, w2) -> Integer.compare(w2.getScore(), w1.getScore()));
        return top5Warriors;
    }


    private <K, V extends Comparable<V>> Map<K, V> sortByValueDescending(Map<K, V> map) {
        return map.entrySet().stream().sorted(Map.Entry.<K, V>comparingByValue().reversed()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }


}
