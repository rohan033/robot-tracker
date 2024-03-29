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
import com.sysdes.rts.application.exception.IllegalStateException;
import com.sysdes.rts.application.exception.InvalidArgumentException;
import com.sysdes.rts.application.exception.ResourceAlreadyExistsException;
import com.sysdes.rts.application.exception.ResourceNotFoundException;
import com.sysdes.rts.application.service.RobotTrackerService;
import com.sysdes.rts.infra.dto.Error;
import com.sysdes.rts.infra.dto.ServiceResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/api/robots")
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class RobotController {

    @Qualifier("CreateRobotCommand")
    private final CreateRobotCommand createRobotCommand;
    @Qualifier("GetRobotCommand")
    private final GetRobotCommand getRobotCommand;
    @Qualifier("MoveRobotCommand")
    private final MoveRobotCommand moveRobotCommand;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<ServiceResponse<CreateRobotResponse>> create(@RequestBody CreateRobotRequest request) throws ResourceAlreadyExistsException {
        try {
            CreateRobotResponse res = createRobotCommand.createRobot(request);
            return new ResponseEntity<>(new ServiceResponse<>(res), HttpStatus.OK);
        } catch (InvalidArgumentException | ResourceAlreadyExistsException e) {
            return new ResponseEntity<>(
                    new ServiceResponse<>(
                            null, new Error(e.getMessage())),
                    HttpStatus.UNPROCESSABLE_ENTITY
            );
        }
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public ResponseEntity<ServiceResponse<GetRobotResponse>> get(@PathVariable String name) {
        try {
            GetRobotResponse res = getRobotCommand.getRobot(new GetRobotRequest(name));
            return new ResponseEntity<>(new ServiceResponse<>(res), HttpStatus.OK);
        } catch (ResourceNotFoundException | InvalidArgumentException e) {
            return new ResponseEntity<>(
                    new ServiceResponse<>(
                            null, new Error(e.getMessage())),
                    HttpStatus.UNPROCESSABLE_ENTITY
            );
        }
    }

    @RequestMapping(value = "/{name}/action/move", method = RequestMethod.POST)
    public ResponseEntity<ServiceResponse<MoveRobotResponse>> move(@RequestBody MoveRobotRequest request) {
        try {
            MoveRobotResponse res = moveRobotCommand.moveRobot(request);
            return new ResponseEntity<>(new ServiceResponse<>(res), HttpStatus.OK);
        } catch (ResourceNotFoundException | InvalidArgumentException | IllegalStateException e) {
            return new ResponseEntity<>(
                    new ServiceResponse<>(
                            null, new Error(e.getMessage())),
                    HttpStatus.UNPROCESSABLE_ENTITY
            );
        }
    }
}
