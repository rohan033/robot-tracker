package com.sysdes.rts.application.api.robot.ports;

import com.sysdes.rts.application.api.robot.dto.request.CreateRobotRequest;
import com.sysdes.rts.application.api.robot.dto.response.CreateRobotResponse;
import com.sysdes.rts.application.exception.InvalidArgumentException;

public interface CreateRobotCommand {
    CreateRobotResponse createRobot(CreateRobotRequest request) throws InvalidArgumentException;
}
