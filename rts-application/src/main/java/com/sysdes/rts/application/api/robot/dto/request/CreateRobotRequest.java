package com.sysdes.rts.application.api.robot.dto.request;

import com.sysdes.rts.application.model.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRobotRequest {
    private String name;
    private Location location;
}
