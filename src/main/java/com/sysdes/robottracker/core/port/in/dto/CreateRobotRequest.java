package com.sysdes.robottracker.core.port.in.dto;

import com.sysdes.robottracker.common.dto.Location;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateRobotRequest {
    private String name;
    private Location location;
}
