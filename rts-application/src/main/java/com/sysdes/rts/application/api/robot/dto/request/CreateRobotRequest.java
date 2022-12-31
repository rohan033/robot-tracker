package com.sysdes.rts.application.api.robot.dto.request;

import com.sysdes.rts.application.model.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateRobotRequest {
    private String name;
    private Location location;
}
