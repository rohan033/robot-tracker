package com.sysdes.robottracker.core.port.out.iface;

import com.sysdes.robottracker.core.domain.model.Robot;

public interface RobotRepository {
    Robot findRobotByName(String name);
}
