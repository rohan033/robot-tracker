package com.sysdes.rts.infra.ui.web;

import com.sysdes.rts.application.api.robot.dto.request.CreateRobotRequest;
import com.sysdes.rts.application.api.robot.dto.request.GetRobotRequest;
import com.sysdes.rts.application.api.robot.dto.request.MoveRobotRequest;
import com.sysdes.rts.application.api.robot.dto.response.CreateRobotResponse;
import com.sysdes.rts.application.api.robot.dto.response.GetRobotResponse;
import com.sysdes.rts.application.api.robot.dto.response.MoveRobotResponse;
import com.sysdes.rts.application.api.robot.ports.CreateRobotCommand;
import com.sysdes.rts.application.api.robot.ports.GetRobotCommand;
import com.sysdes.rts.application.api.robot.ports.MoveRobotCommand;
import com.sysdes.rts.application.enums.RobotStatus;
import com.sysdes.rts.application.exception.IllegalStateException;
import com.sysdes.rts.application.exception.InvalidArgumentException;
import com.sysdes.rts.application.exception.ResourceAlreadyExistsException;
import com.sysdes.rts.application.exception.ResourceNotFoundException;
import com.sysdes.rts.application.model.Location;
import com.sysdes.rts.application.model.Movement;
import com.sysdes.rts.application.service.RobotTrackerService;
import com.sysdes.rts.infra.dto.ServiceResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RobotControllerTest {

    private RobotController robotController;
    private CreateRobotCommand createRobotCommand;
    private GetRobotCommand getRobotCommand;
    private MoveRobotCommand moveRobotCommand;

    @Captor
    private ArgumentCaptor<GetRobotRequest> getRobotRequestArgumentCaptor;
    @Captor
    private ArgumentCaptor<CreateRobotRequest> createRobotRequestArgumentCaptor;
    @Captor
    private ArgumentCaptor<MoveRobotRequest> moveRobotRequestArgumentCaptor;

    @BeforeEach
    public void beforeEach(){
        createRobotCommand = Mockito.mock(CreateRobotCommand.class);
        getRobotCommand = Mockito.mock(GetRobotCommand.class);
        moveRobotCommand = Mockito.mock(MoveRobotCommand.class);
        robotController = new RobotController(createRobotCommand, getRobotCommand, moveRobotCommand);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_create() throws InvalidArgumentException, ResourceAlreadyExistsException {
        CreateRobotRequest req = new CreateRobotRequest("sofia", new Location(0,0));
        CreateRobotResponse res = CreateRobotResponse.builder()
                .name("sofia")
                .currentLocation(new Location(0,0))
                .status(RobotStatus.ALIVE)
                .build();
        when(createRobotCommand.createRobot(any())).thenReturn(res);
        ResponseEntity<ServiceResponse<CreateRobotResponse>> apiResponse =  robotController.create(req);

        Assertions.assertNotNull(apiResponse);
        Assertions.assertEquals(apiResponse.getStatusCode(), HttpStatus.OK);
        Assertions.assertNotNull(apiResponse.getBody());
        Assertions.assertEquals(apiResponse.getBody().getData(), res);
        Assertions.assertNull(apiResponse.getBody().getError());

        verify(createRobotCommand, times(1)).createRobot(createRobotRequestArgumentCaptor.capture());
        Assertions.assertEquals(createRobotRequestArgumentCaptor.getValue(), req);
    }

    @Test
    public void test_create_error() throws InvalidArgumentException, ResourceAlreadyExistsException {
        CreateRobotRequest req = new CreateRobotRequest("sofia", new Location(0,0));
        when(createRobotCommand.createRobot(any())).thenThrow(
                new InvalidArgumentException(new String[]{"Invalid values"})
        );
        ResponseEntity<ServiceResponse<CreateRobotResponse>> apiResponse =  robotController.create(req);

        Assertions.assertNotNull(apiResponse);
        Assertions.assertEquals(apiResponse.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);
        Assertions.assertNotNull(apiResponse.getBody());
        Assertions.assertNull(apiResponse.getBody().getData());
        Assertions.assertNotNull(apiResponse.getBody().getError());
        Assertions.assertEquals(apiResponse.getBody().getError().getMessage(),"[Invalid values]");

        verify(createRobotCommand, times(1)).createRobot(createRobotRequestArgumentCaptor.capture());
        Assertions.assertEquals(createRobotRequestArgumentCaptor.getValue(), req);
    }

    @Test
    public void test_get() throws InvalidArgumentException, ResourceNotFoundException {
        GetRobotResponse res = GetRobotResponse.builder()
                .name("sofia")
                .currentLocation(new Location(0,0))
                .status(RobotStatus.ALIVE)
                .build();
        when(getRobotCommand.getRobot(any())).thenReturn(res);
        ResponseEntity<ServiceResponse<GetRobotResponse>> apiResponse =  robotController.get("sofia");

        Assertions.assertNotNull(apiResponse);
        Assertions.assertEquals(apiResponse.getStatusCode(), HttpStatus.OK);
        Assertions.assertNotNull(apiResponse.getBody());
        Assertions.assertEquals(apiResponse.getBody().getData(), res);
        Assertions.assertNull(apiResponse.getBody().getError());

        verify(getRobotCommand, times(1)).getRobot(getRobotRequestArgumentCaptor.capture());
        Assertions.assertEquals(getRobotRequestArgumentCaptor.getValue().getName(), "sofia");
    }

    @Test
    public void test_get_error() throws InvalidArgumentException, ResourceNotFoundException {
        when(getRobotCommand.getRobot(any())).thenThrow(
                new InvalidArgumentException(new String[]{"Invalid values"})
        );
        ResponseEntity<ServiceResponse<GetRobotResponse>> apiResponse =  robotController.get("sofia");

        Assertions.assertNotNull(apiResponse);
        Assertions.assertEquals(apiResponse.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);
        Assertions.assertNotNull(apiResponse.getBody());
        Assertions.assertNull(apiResponse.getBody().getData());
        Assertions.assertNotNull(apiResponse.getBody().getError());
        Assertions.assertEquals(apiResponse.getBody().getError().getMessage(),"[Invalid values]");

        verify(getRobotCommand, times(1)).getRobot(getRobotRequestArgumentCaptor.capture());
        Assertions.assertEquals(getRobotRequestArgumentCaptor.getValue().getName(), "sofia");
    }


    @Test
    public void test_move() throws InvalidArgumentException, ResourceNotFoundException, IllegalStateException {
        MoveRobotRequest req = new MoveRobotRequest("sofia", Movement.builder().east(1).build());
        MoveRobotResponse res = MoveRobotResponse.builder()
                .name("sofia")
                .oldLocation(new Location(0,0))
                .currentLocation(new Location(1,0))
                .status(RobotStatus.ALIVE)
                .build();
        when(moveRobotCommand.moveRobot(any())).thenReturn(res);
        ResponseEntity<ServiceResponse<MoveRobotResponse>> apiResponse =  robotController.move(req);

        Assertions.assertNotNull(apiResponse);
        Assertions.assertEquals(apiResponse.getStatusCode(), HttpStatus.OK);
        Assertions.assertNotNull(apiResponse.getBody());
        Assertions.assertEquals(apiResponse.getBody().getData(), res);
        Assertions.assertNull(apiResponse.getBody().getError());

        verify(moveRobotCommand, times(1)).moveRobot(moveRobotRequestArgumentCaptor.capture());
        Assertions.assertEquals(moveRobotRequestArgumentCaptor.getValue(), req);
    }

    @Test
    public void test_move_error() throws InvalidArgumentException, ResourceNotFoundException, IllegalStateException {
        MoveRobotRequest req = new MoveRobotRequest("sofia", Movement.builder().east(1).build());
        when(moveRobotCommand.moveRobot(any())).thenThrow(
                new InvalidArgumentException(new String[]{"Invalid values"})
        );
        ResponseEntity<ServiceResponse<MoveRobotResponse>> apiResponse =  robotController.move(req);

        Assertions.assertNotNull(apiResponse);
        Assertions.assertEquals(apiResponse.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);
        Assertions.assertNotNull(apiResponse.getBody());
        Assertions.assertNull(apiResponse.getBody().getData());
        Assertions.assertNotNull(apiResponse.getBody().getError());
        Assertions.assertEquals(apiResponse.getBody().getError().getMessage(),"[Invalid values]");

        verify(moveRobotCommand, times(1)).moveRobot(moveRobotRequestArgumentCaptor.capture());
        Assertions.assertEquals(moveRobotRequestArgumentCaptor.getValue(), req);
    }
}