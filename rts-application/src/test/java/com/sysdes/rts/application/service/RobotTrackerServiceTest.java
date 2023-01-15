package com.sysdes.rts.application.service;

import com.sysdes.rts.application.exception.IllegalStateException;
import com.sysdes.rts.application.exception.ResourceAlreadyExistsException;
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
import com.sysdes.rts.application.spi.events.dto.Event;
import com.sysdes.rts.application.spi.events.dto.EventType;
import com.sysdes.rts.application.spi.events.ports.EventPublisher;
import com.sysdes.rts.application.spi.repository.RobotTrackerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class RobotTrackerServiceTest {

    private CreateRobotCommand createRobotCommand;
    private GetRobotCommand getRobotCommand;
    private MoveRobotCommand moveRobotCommand;

    private RobotTrackerRepository robotTrackerRepository;
    private HoleService holeService;
    private EventPublisher eventPublisher;

    @Captor
    private ArgumentCaptor<Event<MoveRobotResponse>> eventArgumentCaptor;

    @BeforeEach
    public void test_beforeEach() {
        robotTrackerRepository = Mockito.mock(RobotTrackerRepository.class);
        holeService = Mockito.mock(HoleService.class);
        eventPublisher = Mockito.mock(EventPublisher.class);

        createRobotCommand = new RobotTrackerService(robotTrackerRepository, holeService, eventPublisher);
        getRobotCommand = new RobotTrackerService(robotTrackerRepository, holeService, eventPublisher);
        moveRobotCommand = new RobotTrackerService(robotTrackerRepository, holeService, eventPublisher);

        MockitoAnnotations.initMocks(this);
    }

    /********************************** USE CASE: CreteRobot ******************************************************/
    @Test
    void test_createRobot() throws InvalidArgumentException, ResourceAlreadyExistsException {
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

    @Test
    void test_createRobot_RobotAlreadyExists() {
        when(robotTrackerRepository.findRobotByName("robot")).thenReturn(Optional.of(Robot.builder().build()));
        Assertions.assertThrows(ResourceAlreadyExistsException.class,
                () -> createRobotCommand.createRobot(new CreateRobotRequest("robot", new Location(0,0))));
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
    void test_moveRobot() throws InvalidArgumentException, ResourceNotFoundException, IllegalStateException {
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

        when(holeService.getHole(any())).thenReturn(Optional.empty());
        when(eventPublisher.publishEvent(any())).thenReturn(Optional.empty());
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

        verify(eventPublisher, times(1)).publishEvent(eventArgumentCaptor.capture());
        assertEquals(eventArgumentCaptor.getValue().getData(), res);
        assertEquals(eventArgumentCaptor.getValue().getType(), EventType.MOVE_ROBOT);
    }

    @Test
    void test_moveRobot_NegativeMoves() throws InvalidArgumentException, ResourceNotFoundException, IllegalStateException {
        String name = "robot";
        Movement move = Movement.builder()
                .south(1)
                .east(1)
                .build();

        Robot mockRobot = Robot.builder()
                .status(RobotStatus.ALIVE)
                .name(name)
                .currentLocation(new Location(0, 0))
                .build();
        when(holeService.getHole(any())).thenReturn(Optional.empty());
        when(eventPublisher.publishEvent(any())).thenReturn(Optional.empty());
        when(robotTrackerRepository.findRobotByName(eq(name))).thenReturn(Optional.ofNullable(mockRobot));
        when(robotTrackerRepository.save(any(Robot.class))).thenReturn(mockRobot);
        MoveRobotRequest req = new MoveRobotRequest(name, move);
        MoveRobotResponse res = moveRobotCommand.moveRobot(req);

        assertNotNull(res);
        assertEquals(res.getName(), name);
        assertEquals(res.getMovement(), move);
        assertEquals(res.getCurrentLocation(), new Location(1, -1));
        assertEquals(res.getOldLocation(), mockRobot.getCurrentLocation());

        verify(eventPublisher, times(1)).publishEvent(eventArgumentCaptor.capture());
        assertEquals(eventArgumentCaptor.getValue().getData(), res);
        assertEquals(eventArgumentCaptor.getValue().getType(), EventType.MOVE_ROBOT);
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
        verify(holeService,times(0)).getHole(any());
    }

    @Test
    void test_moveRobot_RobotDoesNotExist() {
        when(robotTrackerRepository.findRobotByName(any())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> moveRobotCommand.moveRobot(new MoveRobotRequest("robot", Movement.builder().build())));
        verify(holeService,times(0)).getHole(any());
    }

    @Test
    void test_moveRobot_AliveToDead() throws IllegalStateException, InvalidArgumentException, ResourceNotFoundException {
        String name = "robot";
        Movement move = Movement.builder()
                .south(1)
                .east(1)
                .build();

        Robot mockRobot = Robot.builder()
                .status(RobotStatus.ALIVE)
                .name(name)
                .currentLocation(new Location(0, 0))
                .build();
        when(holeService.getHole(any())).thenReturn(Optional.ofNullable(Location.builder().x(1).y(-1).build()));
        when(eventPublisher.publishEvent(any())).thenReturn(Optional.empty());
        when(robotTrackerRepository.findRobotByName(eq(name))).thenReturn(Optional.of(mockRobot));
        when(robotTrackerRepository.save(any(Robot.class))).thenReturn(mockRobot);
        MoveRobotRequest req = new MoveRobotRequest(name, move);
        MoveRobotResponse res = moveRobotCommand.moveRobot(req);

        assertNotNull(res);
        assertEquals(res.getName(), name);
        assertEquals(res.getMovement(), move);
        assertEquals(res.getCurrentLocation(), new Location(1, -1));
        assertEquals(res.getOldLocation(), mockRobot.getCurrentLocation());
        assertEquals(res.getStatus(), RobotStatus.DEAD);

        verify(eventPublisher, times(1)).publishEvent(eventArgumentCaptor.capture());
        assertEquals(eventArgumentCaptor.getValue().getData(), res);
        assertEquals(eventArgumentCaptor.getValue().getType(), EventType.MOVE_ROBOT);
    }

    @Test
    void test_moveRobot_DeadRobot(){
        String name = "robot";
        Robot mockRobot = Robot.builder()
                .status(RobotStatus.DEAD)
                .name(name)
                .currentLocation(new Location(0, 0))
                .build();
        when(robotTrackerRepository.findRobotByName(eq(name))).thenReturn(Optional.ofNullable(mockRobot));
        Assertions.assertThrows(IllegalStateException.class,
                ()-> moveRobotCommand.moveRobot(new MoveRobotRequest(name, Movement.builder().build())));
    }

}