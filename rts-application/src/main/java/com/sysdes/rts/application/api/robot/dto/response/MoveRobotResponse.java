package com.sysdes.rts.application.api.robot.dto.response;

import com.sysdes.rts.application.enums.RobotStatus;
import com.sysdes.rts.application.model.Location;
import com.sysdes.rts.application.model.Movement;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MoveRobotResponse {
    private String name;
    private Location oldLocation;
    private Location currentLocation;
    private Movement movement;
    private RobotStatus status;
}
