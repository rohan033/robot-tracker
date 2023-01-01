package com.sysdes.rts.dal.storage;

import com.sysdes.rts.dal.entity.Robot;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class InMemoryStore implements Store {

    private final Map<String, Robot> map;

    @Override
    public Robot save(Robot robot) {
        map.put(robot.getName(), robot);
        return map.get(robot.getName());
    }

    @Override
    public Optional<Robot> findByName(String name) {
        return Optional.ofNullable(map.get(name));
    }
}
