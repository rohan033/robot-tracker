package com.sysdes.robottracker.core.port.in.iface;

import com.sun.javaws.exceptions.InvalidArgumentException;
import com.sysdes.robottracker.core.port.in.dto.CreateRobotRequest;
import com.sysdes.robottracker.core.port.in.dto.CreateRobotResponse;

public interface CreateRobotCommand {
    CreateRobotResponse createRobot(CreateRobotRequest request) throws InvalidArgumentException;
}
