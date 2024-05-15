package com.King.repository;

import com.King.repository.entity.Top5Warrior;
import com.King.repository.entity.Warrior;
import org.springframework.stereotype.Component;

@Component
public class WarriorTopWarriorMapper {

    public Top5Warrior top5WarriorMapper(Warrior warrior) {
        return new Top5Warrior(warrior.getWarriorId(), warrior.getName(), warrior.getScore());
    }
}
