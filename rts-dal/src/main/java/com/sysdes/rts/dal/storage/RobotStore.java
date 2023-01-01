package com.sysdes.rts.dal.storage;

import com.sysdes.rts.dal.entity.Robot;
import com.sysdes.rts.dal.repository.RobotRepository;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class RobotStore implements Store{

    private final RobotRepository repository;

    @Override
    public Robot save(Robot robot) {
        return repository.save(robot);
    }

    @Override
    public Optional<com.sysdes.rts.dal.entity.Robot> findByName(String name) {
        return repository.findByName(name);
    }
}
