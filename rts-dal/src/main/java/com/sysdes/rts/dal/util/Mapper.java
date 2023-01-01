package com.sysdes.rts.dal.util;

import com.sysdes.rts.application.enums.RobotStatus;
import com.sysdes.rts.application.model.Location;
import com.sysdes.rts.dal.entity.Robot;

public class Mapper {

    private final static Serdes serdes = new Serdes();

    public static Robot toEntity(com.sysdes.rts.application.model.Robot robot) {
        return Robot.builder()
                .name(robot.getName())
                .status(robot.getStatus().name())
                .location(serdes.toJson(robot.getCurrentLocation()).get())
                .build();
    }

    public static com.sysdes.rts.application.model.Robot toModel(Robot robot) {
        return com.sysdes.rts.application.model.Robot.builder()
                .name(robot.getName())
                .status(RobotStatus.valueOf(robot.getStatus()))
                .currentLocation(serdes.fromJson(robot.getLocation(), Location.class).get())
                .build();
    }
}
