package com.sysdes.robottracker.core.port.in.dto;

import com.sysdes.robottracker.common.dto.Movement;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MoveRobotRequest {
    private String name;
    private Movement movement;
}
