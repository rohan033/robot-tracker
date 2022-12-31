package com.sysdes.rts.application.api.robot.ports;

import com.sysdes.rts.application.api.robot.dto.request.MoveRobotRequest;
import com.sysdes.rts.application.api.robot.dto.response.MoveRobotResponse;
import com.sysdes.rts.application.exception.InvalidArgumentException;
import com.sysdes.rts.application.exception.ResourceNotFoundException;

public interface MoveRobotCommand {
    MoveRobotResponse moveRobot(MoveRobotRequest request) throws InvalidArgumentException, ResourceNotFoundException;
}
