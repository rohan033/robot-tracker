package com.sysdes.robottracker.core.domain.model;

import com.sysdes.robottracker.common.dto.Location;
import com.sysdes.robottracker.common.dto.Movement;
import com.sysdes.robottracker.common.enums.RobotStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
public class Robot {
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
}
