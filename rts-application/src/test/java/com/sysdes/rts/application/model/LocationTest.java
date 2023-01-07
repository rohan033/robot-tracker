package com.sysdes.rts.application.model;

import com.sysdes.rts.application.api.robot.dto.response.CreateRobotResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {
    private Location location;

    @BeforeEach
    public void test_beforeEach() {
        location = Location.builder().x(0).y(0).build();
    }

    @Test
    void test_NotNulls(){
        assertNotNull(Location.builder().toString());
        assertNotNull(new Location().toBuilder());
        assertNotNull(location.toString());
        assertNotNull(location.getX());
        assertNotNull(location.getY());

        Location location1 = Location.builder().y(0).build();
        assertNull(location1.getX());
        assertNotNull(location1.getY());
    }

    @Test
    void test_Equals(){
        Location location1 = new Location(0,0);
        assertEquals(location1, location);

        Location location2 = new Location(0,1);
        assertNotEquals(location1, location2);
    }

    @Test
    void test_HashCode(){
        assertEquals(location.hashCode(), Objects.hash(0,0));
        assertNotEquals(location.hashCode(), Objects.hash(1,0));
    }

    @Test
    void test_applyMovement_NullMovement(){
        Location newLocation = location.applyMovement(null);
        assertNotNull(newLocation);
        assert newLocation != location;

        assertEquals(location.getX(), newLocation.getX());
        assertEquals(location.getY(), newLocation.getY());
    }

    @Test
    void test_applyMovement_moveEast(){
        Location newLocation = location.applyMovement(Movement.builder().east(1).build());
        Location expected = new Location(1,0);

        assertNotNull(newLocation);
        assertEquals(expected, newLocation);
    }

    @Test
    void test_applyMovement_moveNorth(){
        Location newLocation = location.applyMovement(Movement.builder().north(1).build());
        Location expected = new Location(0,1);

        assertNotNull(newLocation);
        assertEquals(expected, newLocation);
    }

    @Test
    void test_applyMovement_moveSouth(){
        Location newLocation = location.applyMovement(Movement.builder().south(1).build());
        Location expected = new Location(0,-1);

        assertNotNull(newLocation);
        assertEquals(expected, newLocation);
    }

    @Test
    void test_applyMovement_moveWest(){
        Location newLocation = location.applyMovement(Movement.builder().west(1).build());
        Location expected = new Location(-1,0);

        assertNotNull(newLocation);
        assertEquals(expected, newLocation);
    }

}