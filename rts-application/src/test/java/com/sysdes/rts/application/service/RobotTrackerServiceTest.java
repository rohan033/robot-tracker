package com.sysdes.rts.application.service;

import com.sysdes.rts.application.model.Location;
import com.sysdes.rts.application.model.Movement;
import com.sysdes.rts.application.api.robot.dto.request.CreateRobotRequest;
import com.sysdes.rts.application.api.robot.dto.request.GetRobotRequest;
import com.sysdes.rts.application.api.robot.dto.request.MoveRobotRequest;
import com.sysdes.rts.application.api.robot.dto.response.CreateRobotResponse;
import com.sysdes.rts.application.api.robot.dto.response.GetRobotResponse;
import com.sysdes.rts.application.api.robot.dto.response.MoveRobotResponse;
import com.sysdes.rts.application.enums.RobotStatus;
import com.sysdes.rts.application.exception.InvalidArgumentException;
import com.sysdes.rts.application.exception.ResourceNotFoundException;
import com.sysdes.rts.application.model.Robot;
import com.sysdes.rts.application.api.robot.ports.CreateRobotCommand;
import com.sysdes.rts.application.api.robot.ports.GetRobotCommand;
import com.sysdes.rts.application.api.robot.ports.MoveRobotCommand;
import com.sysdes.rts.application.spi.repository.RobotTrackerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class RobotTrackerServiceTest {

    private CreateRobotCommand createRobotCommand;
    private GetRobotCommand getRobotCommand;
    private MoveRobotCommand moveRobotCommand;

    private RobotTrackerRepository robotTrackerRepository;

    @BeforeEach
    public void test_beforeEach() {
        robotTrackerRepository = Mockito.mock(RobotTrackerRepository.class);
        createRobotCommand = new RobotTrackerService(robotTrackerRepository);
        getRobotCommand = new RobotTrackerService(robotTrackerRepository);
        moveRobotCommand = new RobotTrackerService(robotTrackerRepository);
    }

    /********************************** USE CASE: CreteRobot ******************************************************/
    @Test
    void test_createRobot() throws InvalidArgumentException {
        final String name = "robot";
        final Location location = new Location(0, 0);
        Robot mockRobot = Robot.builder()
                .name(name)
                .currentLocation(location)
                .status(RobotStatus.ALIVE)
                .build();

        when(robotTrackerRepository.save(any(Robot.class))).thenReturn(mockRobot);
        CreateRobotRequest req = new CreateRobotRequest(name, location);
        CreateRobotResponse res = createRobotCommand.createRobot(req);

        assertNotNull(res);
        assertEquals(req.getName(), res.getName());
        assertEquals(req.getLocation(), res.getCurrentLocation());
        assertEquals(RobotStatus.ALIVE, res.getStatus());
    }

    @Test
    void test_createRobot_NullReq() {
        Assertions.assertThrows(InvalidArgumentException.class,
                () -> createRobotCommand.createRobot(null));
    }

    @Test
    void test_createRobot_NullRobotName() {
        Assertions.assertThrows(InvalidArgumentException.class,
                () -> createRobotCommand.createRobot(new CreateRobotRequest(null, new Location(0, 0))));
    }

    @Test
    void test_createRobot_NullRobotLocation() {
        Assertions.assertThrows(InvalidArgumentException.class,
                () -> createRobotCommand.createRobot(new CreateRobotRequest("robot", null)));
    }

    /********************************** USE CASE: GetRobot ********************************************************/
    @Test
    void test_getRobot() throws InvalidArgumentException, ResourceNotFoundException {
        final String name = "robot";
        Robot mockRobot = Robot.builder()
                .name(name)
                .currentLocation(new Location(0, 0))
                .status(RobotStatus.ALIVE)
                .build();
        when(robotTrackerRepository.findRobotByName(eq(name))).thenReturn(Optional.ofNullable(mockRobot));

        GetRobotRequest req = new GetRobotRequest(name);
        GetRobotResponse res = getRobotCommand.getRobot(req);

        assertNotNull(res);
        assertNotNull(res.getName());
        assertNotNull(res.getCurrentLocation());
        assertNotNull(res.getStatus());
    }

    @Test
    void test_getRobot_NullRequest() {
        Assertions.assertThrows(InvalidArgumentException.class,
                () -> getRobotCommand.getRobot(null));
    }

    @Test
    void test_getRobot_NullRobotName() {
        Assertions.assertThrows(InvalidArgumentException.class,
                () -> getRobotCommand.getRobot(new GetRobotRequest(null)));
    }

    @Test
    void test_getRobot_RobotDoesNotExist() {
        when(robotTrackerRepository.findRobotByName(any())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> getRobotCommand.getRobot(new GetRobotRequest("robot")));
    }

    /********************************** USE CASE: MoveRobot ********************************************************/

    @Test
    void test_moveRobot() throws InvalidArgumentException, ResourceNotFoundException {
        String name = "robot";
        Movement move = Movement.builder()
                .north(1)
                .east(1)
                .build();

        Robot mockRobot = Robot.builder()
                .id(1)
                .name(name)
                .currentLocation(new Location(0, 0))
                .status(RobotStatus.ALIVE)
                .build();
        when(robotTrackerRepository.findRobotByName(eq(name))).thenReturn(Optional.ofNullable(mockRobot));
        when(robotTrackerRepository.save(any(Robot.class))).thenReturn(mockRobot);
        MoveRobotRequest req = new MoveRobotRequest(name, move);
        MoveRobotResponse res = moveRobotCommand.moveRobot(req);

        assertNotNull(res);
        assertEquals(res.getName(), name);
        assertEquals(res.getMovement(), move);
        assertEquals(res.getCurrentLocation(), new Location(1, 1));
        assertEquals(res.getOldLocation(), mockRobot.getCurrentLocation());
        assertEquals(res.getStatus(), mockRobot.getStatus());
    }

    @Test
    void test_moveRobot_NegativeMoves() throws InvalidArgumentException, ResourceNotFoundException {
        String name = "robot";
        Movement move = Movement.builder()
                .south(1)
                .east(1)
                .build();

        Robot mockRobot = Robot.builder()
                .name(name)
                .currentLocation(new Location(0, 0))
                .build();
        when(robotTrackerRepository.findRobotByName(eq(name))).thenReturn(Optional.ofNullable(mockRobot));
        when(robotTrackerRepository.save(any(Robot.class))).thenReturn(mockRobot);
        MoveRobotRequest req = new MoveRobotRequest(name, move);
        MoveRobotResponse res = moveRobotCommand.moveRobot(req);

        assertNotNull(res);
        assertEquals(res.getName(), name);
        assertEquals(res.getMovement(), move);
        assertEquals(res.getCurrentLocation(), new Location(1, -1));
        assertEquals(res.getOldLocation(), mockRobot.getCurrentLocation());
    }

    @Test
    void test_moveRobot_NullRequest() {
        Assertions.assertThrows(InvalidArgumentException.class,
                () -> moveRobotCommand.moveRobot(null)
        );
    }

    @Test
    void test_moveRobot_NullName() {
        Assertions.assertThrows(InvalidArgumentException.class,
                () -> moveRobotCommand.moveRobot(new MoveRobotRequest(null, Movement.builder().build()))
        );
    }

    @Test
    void test_moveRobot_NullMovement() {
        Assertions.assertThrows(InvalidArgumentException.class,
                () -> moveRobotCommand.moveRobot(new MoveRobotRequest("robot", null))
        );
    }

    @Test
    void test_moveRobot_RobotDoesNotExist() {
        when(robotTrackerRepository.findRobotByName(any())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> moveRobotCommand.moveRobot(new MoveRobotRequest("robot", Movement.builder().build())));
    }
}