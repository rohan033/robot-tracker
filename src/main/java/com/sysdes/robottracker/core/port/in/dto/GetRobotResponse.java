package com.sysdes.robottracker.core.port.in.dto;

import com.sysdes.robottracker.common.dto.Location;
import com.sysdes.robottracker.common.enums.RobotStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetRobotResponse{
    private String name;
    private Location currentLocation;
    private RobotStatus status;
}
