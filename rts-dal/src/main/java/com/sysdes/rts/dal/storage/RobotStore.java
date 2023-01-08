package com.sysdes.rts.dal.storage;

import com.sysdes.rts.dal.entity.Hole;
import com.sysdes.rts.dal.entity.Robot;
import com.sysdes.rts.dal.repository.HoleRepository;
import com.sysdes.rts.dal.repository.RobotRepository;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class RobotStore implements Store{

    private final RobotRepository robotRepository;
    private final HoleRepository holeRepository;

    @Override
    public Robot save(Robot robot) {
        return robotRepository.save(robot);
    }

    @Override
    public Optional<com.sysdes.rts.dal.entity.Robot> findByName(String name) {
        return robotRepository.findByName(name);
    }

    @Override
    public Optional<Hole> findHole(Integer x, Integer y) {
        return holeRepository.findByXAndY(x, y);
    }

    @Override
    public Hole saveHole(Hole hole) {
        return holeRepository.save(hole);
    }
}
