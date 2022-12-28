package com.sysdes.robottracker.common.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@EqualsAndHashCode
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class Location {
    private final int x;
    private final int y;

    public Location applyMovement(Movement movement) {
        if (movement == null) {
            return this.toBuilder().build();
        }

        LocationBuilder newBuilder = this.toBuilder();
        if (movement.getEast() != null) {
            newBuilder.x(newBuilder.x + movement.getEast());
        }
        if (movement.getNorth() != null) {
            newBuilder.y(newBuilder.y + movement.getNorth());
        }
        if (movement.getSouth() != null) {
            newBuilder.y(newBuilder.y - movement.getSouth());
        }
        if (movement.getWest() != null) {
            newBuilder.x(newBuilder.x - movement.getWest());
        }

        return newBuilder.build();
    }
}
