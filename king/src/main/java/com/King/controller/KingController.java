package com.King.controller;

import com.King.repository.entity.Top5Warrior;
import com.King.repository.entity.Warrior;
import com.King.service.WarriorKillCountServiceInterface;
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
    private WarriorKillCountServiceInterface warriorKillCountServiceInterface;

    @PostMapping("/registerWarriors")
    public ResponseEntity<String> registerWarriors(@RequestBody Map<String, String> warriorCodeName) throws JsonProcessingException {
        warriorKillCountServiceInterface.saveWarriors(warriorCodeName);
        return ResponseEntity.ok("registeredWarriors");
    }

    @GetMapping("/registeredWarriors")
    public ResponseEntity<List<Warrior>> getRegisteredWarriors() {

        return ResponseEntity.ok(warriorKillCountServiceInterface.getRegisteredWarriors());
    }

    @GetMapping("/top5Players")
    public ResponseEntity<List<Top5Warrior>> getTop5Warriors() {

        return ResponseEntity.ok(warriorKillCountServiceInterface.getTop5Warriors());
    }


}
