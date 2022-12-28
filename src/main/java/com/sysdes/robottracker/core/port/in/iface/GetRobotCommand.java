package com.sysdes.robottracker.core.port.in.iface;

import com.sun.javaws.exceptions.InvalidArgumentException;
import com.sysdes.robottracker.core.port.in.dto.GetRobotRequest;
import com.sysdes.robottracker.core.port.in.dto.GetRobotResponse;

public interface GetRobotCommand {
    GetRobotResponse getRobot(GetRobotRequest request) throws InvalidArgumentException;
}
