package com.sysdes.rts.dal.storage;

import com.sysdes.rts.dal.entity.Hole;
import com.sysdes.rts.dal.entity.Robot;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class InMemoryStore implements Store {

    private final Map<String, Robot> robots;
    private final Map<String, Hole> holes;

    @Override
    public Robot save(Robot robot) {
        robots.put(robot.getName(), robot);
        return robots.get(robot.getName());
    }

    @Override
    public Optional<Robot> findByName(String name) {
        return Optional.ofNullable(robots.get(name));
    }

    @Override
    public Optional<Hole> findHole(Integer x, Integer y) {
        return Optional.ofNullable(holes.get(x.toString() + ":" + y.toString()));
    }

    @Override
    public Hole saveHole(Hole hole) {
        holes.put(hole.getX().toString()+":"+hole.getY().toString(), hole);
        return holes.get(hole.getX().toString()+":"+hole.getY().toString());
    }
}
