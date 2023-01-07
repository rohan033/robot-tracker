package com.sysdes.rts.application.model;

import lombok.*;

import java.util.Objects;


@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    private Integer x;
    private Integer y;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return x == location.x && y == location.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
