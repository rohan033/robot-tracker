package com.sysdes.robottracker.core.port.in.dto;

import com.sysdes.robottracker.common.dto.Location;
import com.sysdes.robottracker.common.dto.Movement;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MoveRobotResponse {
    private String name;
    private Location oldLocation;
    private Location currentLocation;
    private Movement movement;
}
