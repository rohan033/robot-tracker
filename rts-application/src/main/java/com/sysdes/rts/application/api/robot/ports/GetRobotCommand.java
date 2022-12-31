package com.sysdes.rts.application.api.robot.ports;

import com.sysdes.rts.application.api.robot.dto.request.GetRobotRequest;
import com.sysdes.rts.application.api.robot.dto.response.GetRobotResponse;
import com.sysdes.rts.application.exception.InvalidArgumentException;
import com.sysdes.rts.application.exception.ResourceNotFoundException;

public interface GetRobotCommand {
    GetRobotResponse getRobot(GetRobotRequest request) throws InvalidArgumentException, ResourceNotFoundException;
}
