package com.King.config;

import com.King.constants.AppConstants;
import com.King.service.WarriorKillCountServiceImpl;
import com.King.service.WarriorKillCountServiceInterface;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;


import java.util.HashMap;
import java.util.Map;


@Configuration
public class KafkaConfig {

    //private final Logger logger = LoggerFactory.getLogger(KafkaService.class);


    private final ObjectMapper objectMapper;

    private final WarriorKillCountServiceInterface warriorKillCountServiceInterface;

    public KafkaConfig(WarriorKillCountServiceInterface warriorKillCountServiceInterface) {
        this.warriorKillCountServiceInterface = warriorKillCountServiceInterface;
        this.objectMapper = new ObjectMapper();
    }


    @KafkaListener(topics = AppConstants.SCOREBOARD_TOPIC, groupId = AppConstants.GROUP_ID)
    public void updateScores(String killerKill) throws JsonParseException, JsonMappingException {

        Map data = new HashMap();
        try {

            data = objectMapper.readValue(killerKill, Map.class);
            // Process the data here

            warriorKillCountServiceInterface.ProcessData(data);
        } catch (Exception e) {
            e.getMessage();

        }

        System.out.println(data.toString());
    }


}
