package com.sysdes.robottracker.core.port.in.iface;

import com.sun.javaws.exceptions.InvalidArgumentException;
import com.sysdes.robottracker.core.port.in.dto.MoveRobotRequest;
import com.sysdes.robottracker.core.port.in.dto.MoveRobotResponse;

public interface MoveRobotCommand {
    MoveRobotResponse moveRobot(MoveRobotRequest request) throws InvalidArgumentException;
}
