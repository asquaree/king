package com.King.repository;

import com.King.repository.entity.Warrior;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class WarriorMapper {

    public List<Warrior> mapWarriorMapToEntityList(Map<String,String> warriorCodeName) {
        List<Warrior> warriorList = new ArrayList<Warrior>();
        warriorCodeName.forEach( (warriorCode,name) ->warriorList.add(new Warrior(warriorCode,name)));

        return warriorList;
    }

}
