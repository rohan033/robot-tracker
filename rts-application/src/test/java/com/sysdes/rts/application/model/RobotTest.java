package com.sysdes.rts.application.model;

import com.sysdes.rts.application.enums.RobotStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RobotTest {
    private Robot robot;
    @BeforeEach
    public void test_beforeEach(){
        robot = Robot.builder().name("robot").currentLocation(new Location(0,0)).status(RobotStatus.ALIVE).build();
    }

    @Test
    void test_NullId(){
        assertNull(robot.getId());
    }

    @Test
    void test_move() {
        Movement movement = Movement.builder().east(1).north(1).build();
        Robot movedRobot = robot.move(movement);

        assertNotNull(movedRobot);
        assertEquals(movedRobot.getName(), robot.getName());
        assertEquals(movedRobot.getCurrentLocation(), new Location(1,1));
        assertEquals(movedRobot.getStatus(), robot.getStatus());
    }

    @Test
    void test_move_NullMovement() {
        Robot movedRobot = robot.move(null);

        assertNotNull(movedRobot);
        assert movedRobot != robot;

        assertEquals(movedRobot.getName(), robot.getName());
        assertEquals(movedRobot.getCurrentLocation(), robot.getCurrentLocation());
        assertEquals(movedRobot.getStatus(), robot.getStatus());
    }

    @Test
    void test_builder_ToString(){
        Robot.RobotBuilder builder = Robot.builder();
        String str = builder.toString();
        assertEquals(str, "Robot.RobotBuilder(id=null, name=null, currentLocation=null, status=null)");
    }
}