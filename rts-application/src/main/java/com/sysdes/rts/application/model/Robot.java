package com.sysdes.rts.application.model;

import com.sysdes.rts.application.enums.RobotStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
public class Robot {
    private Integer id;
    private String name;
    private Location currentLocation;
    private RobotStatus status;

    public Robot move(Movement movement) {
        if(movement == null){
            return this.toBuilder().build();
        }

        Location newLocation = this.currentLocation.applyMovement(movement);
        return this.toBuilder().currentLocation(newLocation).build();
    }

    public Robot move(Movement movement, boolean isHole) {
        Robot robot = move(movement);
        if(!isHole){
            return robot;
        }
        return robot.toBuilder().status(RobotStatus.DEAD).build();
    }
}
