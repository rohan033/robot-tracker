package com.sysdes.rts.application.api.robot.ports;

import com.sysdes.rts.application.api.robot.dto.request.CreateRobotRequest;
import com.sysdes.rts.application.api.robot.dto.response.CreateRobotResponse;
import com.sysdes.rts.application.exception.InvalidArgumentException;
import com.sysdes.rts.application.exception.ResourceAlreadyExistsException;

public interface CreateRobotCommand {
    CreateRobotResponse createRobot(CreateRobotRequest request) throws InvalidArgumentException, ResourceAlreadyExistsException;
}
