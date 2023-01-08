package com.sysdes.rts.application.utils;


import com.sysdes.rts.application.api.robot.dto.request.CreateHoleRequest;
import com.sysdes.rts.application.api.robot.dto.request.CreateRobotRequest;
import com.sysdes.rts.application.api.robot.dto.request.MoveRobotRequest;
import com.sysdes.rts.application.api.robot.dto.response.CreateHoleResponse;
import com.sysdes.rts.application.api.robot.dto.response.CreateRobotResponse;
import com.sysdes.rts.application.api.robot.dto.response.GetRobotResponse;
import com.sysdes.rts.application.api.robot.dto.response.MoveRobotResponse;
import com.sysdes.rts.application.enums.RobotStatus;
import com.sysdes.rts.application.model.Location;
import com.sysdes.rts.application.model.Robot;

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
        return MoveRobotResponse.builder()
                .name(request.getName())
                .oldLocation(robot.getCurrentLocation())
                .currentLocation(movedRobot.getCurrentLocation())
                .movement(request.getMovement())
                .status(movedRobot.getStatus())
                .build();
    }

    public static Robot toNewRobot(CreateRobotRequest request) {
        return Robot.builder()
                .name(request.getName())
                .currentLocation(request.getLocation())
                .status(RobotStatus.ALIVE)
                .build();
    }

    public static CreateHoleResponse toCreateHoleResponse(Location location){
        return CreateHoleResponse.builder()
                .x(location.getX())
                .y(location.getY())
                .build();
    }

    public static Location toLocation(CreateHoleRequest request){
        return Location.builder()
                .x(request.getX())
                .y(request.getY())
                .build();
    }


}
