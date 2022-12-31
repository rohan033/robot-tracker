package com.sysdes.rts.application.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {
    private Location location;

    @BeforeEach
    public void beforeEach() {
        location = new Location(0, 0);
    }

    @Test
    void applyMovement_NullMovement(){
        Location newLocation = location.applyMovement(null);
        assertNotNull(newLocation);
        assert newLocation != location;

        assertEquals(location.getX(), newLocation.getX());
        assertEquals(location.getY(), newLocation.getY());
    }

    @Test
    void applyMovement_moveEast(){
        Location newLocation = location.applyMovement(Movement.builder().east(1).build());
        Location expected = new Location(1,0);

        assertEquals(expected, newLocation);
    }

    @Test
    void applyMovement_moveNorth(){
        Location newLocation = location.applyMovement(Movement.builder().north(1).build());
        Location expected = new Location(0,1);

        assertEquals(expected, newLocation);
    }

    @Test
    void applyMovement_moveSouth(){
        Location newLocation = location.applyMovement(Movement.builder().south(1).build());
        Location expected = new Location(0,-1);

        assertEquals(expected, newLocation);
    }

    @Test
    void applyMovement_moveWest(){
        Location newLocation = location.applyMovement(Movement.builder().west(1).build());
        Location expected = new Location(-1,0);

        assertEquals(expected, newLocation);
    }

}