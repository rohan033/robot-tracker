package com.sysdes.robottracker.core.adapter.in;

import com.sun.javaws.exceptions.InvalidArgumentException;
import com.sysdes.robottracker.common.dto.Location;
import com.sysdes.robottracker.common.dto.Movement;
import com.sysdes.robottracker.common.enums.RobotStatus;
import com.sysdes.robottracker.core.domain.model.Robot;
import com.sysdes.robottracker.core.port.in.dto.*;
import com.sysdes.robottracker.core.port.in.iface.CreateRobotCommand;
import com.sysdes.robottracker.core.port.in.iface.GetRobotCommand;
import com.sysdes.robottracker.core.port.in.iface.MoveRobotCommand;
import com.sysdes.robottracker.core.port.out.iface.RobotRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class RobotTrackerServiceTest {

    private CreateRobotCommand createRobotCommand;
    private GetRobotCommand getRobotCommand;
    private MoveRobotCommand moveRobotCommand;

    private RobotRepository robotRepository;

    @BeforeEach
    public void beforeEach() {
        robotRepository = Mockito.mock(RobotRepository.class);
        createRobotCommand = new RobotTrackerService(robotRepository);
        getRobotCommand = new RobotTrackerService(robotRepository);
        moveRobotCommand = new RobotTrackerService(robotRepository);
    }

    /********************************** USE CASE: CreteRobot ******************************************************/
    @Test
    void createRobot() throws InvalidArgumentException {
        CreateRobotRequest req = new CreateRobotRequest("robot", new Location(0, 0));
        CreateRobotResponse res = createRobotCommand.createRobot(req);

        assertNotNull(res);
        assertEquals(req.getName(), res.getName());
        assertEquals(req.getLocation(), res.getCurrentLocation());
        assertEquals(RobotStatus.ALIVE, res.getStatus());
    }

    @Test
    void createRobot_NullReq() {
        Assertions.assertThrows(InvalidArgumentException.class,
                () -> createRobotCommand.createRobot(null));
    }

    @Test
    void createRobot_NullRobotName() {
        Assertions.assertThrows(InvalidArgumentException.class,
                () -> createRobotCommand.createRobot(new CreateRobotRequest(null, new Location(0, 0))));
    }

    @Test
    void createRobot_NullRobotLocation() {
        Assertions.assertThrows(InvalidArgumentException.class,
                () -> createRobotCommand.createRobot(new CreateRobotRequest("robot", null)));
    }

    /********************************** USE CASE: GetRobot ********************************************************/
    @Test
    void getRobot() throws InvalidArgumentException {
        GetRobotRequest req = new GetRobotRequest("robot");
        GetRobotResponse res = getRobotCommand.getRobot(req);

        assertNotNull(res);
        assertNotNull(res.getName());
        assertNotNull(res.getCurrentLocation());
        assertNotNull(res.getStatus());
    }

    @Test
    void getRobot_NullRequest() {
        Assertions.assertThrows(InvalidArgumentException.class,
                () -> getRobotCommand.getRobot(null));
    }

    @Test
    void getRobot_NullRobotName() {
        Assertions.assertThrows(InvalidArgumentException.class,
                () -> getRobotCommand.getRobot(new GetRobotRequest(null)));
    }

    /********************************** USE CASE: MoveRobot ********************************************************/

    @Test
    void moveRobot() throws InvalidArgumentException {
        String name = "robot";
        Movement move = Movement.builder()
                .north(1)
                .east(1)
                .build();

        Robot mockRobot = Robot.builder()
                .name(name)
                .currentLocation(new Location(0, 0))
                .build();
        when(robotRepository.findRobotByName(eq(name))).thenReturn(mockRobot);
        MoveRobotRequest req = new MoveRobotRequest(name, move);
        MoveRobotResponse res = moveRobotCommand.moveRobot(req);

        assertNotNull(res);
        assertEquals(res.getName(), name);
        assertEquals(res.getMovement(), move);
        assertEquals(res.getCurrentLocation(), new Location(1, 1));
        assertEquals(res.getOldLocation(), mockRobot.getCurrentLocation());
    }

    @Test
    void moveRobot_NegativeMoves() throws InvalidArgumentException {
        String name = "robot";
        Movement move = Movement.builder()
                .south(1)
                .east(1)
                .build();

        Robot mockRobot = Robot.builder()
                .name(name)
                .currentLocation(new Location(0, 0))
                .build();
        when(robotRepository.findRobotByName(eq(name))).thenReturn(mockRobot);
        MoveRobotRequest req = new MoveRobotRequest(name, move);
        MoveRobotResponse res = moveRobotCommand.moveRobot(req);

        assertNotNull(res);
        assertEquals(res.getName(), name);
        assertEquals(res.getMovement(), move);
        assertEquals(res.getCurrentLocation(), new Location(1, -1));
        assertEquals(res.getOldLocation(), mockRobot.getCurrentLocation());
    }

    @Test
    void moveRobot_NullRequest() {
        Assertions.assertThrows(InvalidArgumentException.class,
                () -> moveRobotCommand.moveRobot(null)
        );
    }

    @Test
    void moveRobot_NullName() {
        Assertions.assertThrows(InvalidArgumentException.class,
                () -> moveRobotCommand.moveRobot(new MoveRobotRequest(null, Movement.builder().build()))
        );
    }

    @Test
    void moveRobot_NullMovement() {
        Assertions.assertThrows(InvalidArgumentException.class,
                () -> moveRobotCommand.moveRobot(new MoveRobotRequest("robot", null))
        );
    }

}