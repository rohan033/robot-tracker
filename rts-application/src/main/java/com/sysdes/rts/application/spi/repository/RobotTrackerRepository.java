package com.sysdes.rts.application.spi.repository;


import com.sysdes.rts.application.model.Robot;

import java.util.Optional;

public interface RobotTrackerRepository {
    Robot save(Robot robot);
    Optional<com.sysdes.rts.application.model.Robot> findRobotByName(String name);
}
