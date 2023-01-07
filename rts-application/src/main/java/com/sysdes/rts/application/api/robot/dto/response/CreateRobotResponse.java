package com.sysdes.rts.application.api.robot.dto.response;


import com.sysdes.rts.application.model.Location;
import com.sysdes.rts.application.enums.RobotStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateRobotResponse {
    private String name;
    private Location currentLocation;
    private RobotStatus status;
    private Error error;
}
