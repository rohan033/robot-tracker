package com.sysdes.robottracker.core.utils;

import com.sysdes.robottracker.common.enums.RobotStatus;
import com.sysdes.robottracker.core.domain.model.Robot;
import com.sysdes.robottracker.core.port.in.dto.*;

public class Mapper {

    public static GetRobotResponse getGetResponse(Robot robot) {
        return GetRobotResponse.builder()
                .name(robot.getName())
                .currentLocation(robot.getCurrentLocation())
                .status(robot.getStatus())
                .build();
    }

    public static CreateRobotResponse getCreateResponse(Robot robot) {
        return CreateRobotResponse.builder()
                .name(robot.getName())
                .currentLocation(robot.getCurrentLocation())
                .status(robot.getStatus())
                .build();
    }

    public static MoveRobotResponse getMoveResponse(MoveRobotRequest request, Robot robot, Robot movedRobot) {
        return new MoveRobotResponse(
                request.getName(),
                robot.getCurrentLocation(),
                movedRobot.getCurrentLocation(),
                request.getMovement()
        );
    }

    public static Robot toRobot(CreateRobotRequest request) {
        return Robot.builder()
                .name(request.getName())
                .currentLocation(request.getLocation())
                .status(RobotStatus.ALIVE)
                .build();
    }
}
