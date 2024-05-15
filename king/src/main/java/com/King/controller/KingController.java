package com.King.controller;

import com.King.repository.entity.Top5Warrior;
import com.King.repository.entity.Warrior;
import com.King.service.WarriorKillCountServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/king")
public class KingController {


    @Autowired
    private WarriorKillCountServiceImpl warriorKillCountServiceImpl;

    @PostMapping("/registerWarriors")
    public ResponseEntity<String> registerWarriors(@RequestBody Map<String, String> warriorCodeName) throws JsonProcessingException {
        // Access data from the map
        warriorKillCountServiceImpl.saveWarriors(warriorCodeName);

        // Return a response
        return ResponseEntity.ok("registeredWarriors");
    }

    @GetMapping("/registeredWarriors")
    public ResponseEntity<List<Warrior>> getRegisteredWarriors() {

        return ResponseEntity.ok(warriorKillCountServiceImpl.getRegisteredWarriors());
    }

    @GetMapping("/top5Players")
    public ResponseEntity<List<Top5Warrior>> getTop5Warriors() {

        return ResponseEntity.ok(warriorKillCountServiceImpl.getTop5Warriors());
    }


}
