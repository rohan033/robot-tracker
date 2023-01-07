package com.sysdes.rts.application.api.robot.dto.request;

import com.sysdes.rts.application.model.Movement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MoveRobotRequest {
    private String name;
    private Movement movement;
}
