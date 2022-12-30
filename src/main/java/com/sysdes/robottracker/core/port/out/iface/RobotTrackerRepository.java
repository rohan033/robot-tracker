package com.sysdes.robottracker.core.port.out.iface;

import com.sysdes.robottracker.core.domain.model.Robot;

import java.util.Optional;

public interface RobotTrackerRepository {
    Robot save(Robot robot);
    Optional<Robot> findRobotByName(String name);
}
