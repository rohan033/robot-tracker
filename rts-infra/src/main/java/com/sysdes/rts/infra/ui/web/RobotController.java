package com.sysdes.rts.infra.ui.web;

import com.sysdes.rts.application.api.robot.dto.request.CreateRobotRequest;
import com.sysdes.rts.application.api.robot.dto.request.GetRobotRequest;
import com.sysdes.rts.application.api.robot.dto.request.MoveRobotRequest;
import com.sysdes.rts.application.api.robot.dto.response.CreateRobotResponse;
import com.sysdes.rts.application.api.robot.dto.response.Error;
import com.sysdes.rts.application.api.robot.dto.response.GetRobotResponse;
import com.sysdes.rts.application.api.robot.dto.response.MoveRobotResponse;
import com.sysdes.rts.application.exception.InvalidArgumentException;
import com.sysdes.rts.application.exception.ResourceNotFoundException;
import com.sysdes.rts.application.service.RobotTrackerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/api/robots")
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class RobotController {
    private final RobotTrackerService robotTrackerService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<CreateRobotResponse> create(@RequestBody CreateRobotRequest request) {
        try {
            CreateRobotResponse res = robotTrackerService.createRobot(request);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (InvalidArgumentException e) {
            return new ResponseEntity<>(CreateRobotResponse.builder().error(new Error(e.getMessage())).build(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public ResponseEntity<GetRobotResponse> get(@PathVariable String name) {
        try {
            GetRobotResponse res = robotTrackerService.getRobot(new GetRobotRequest(name));
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (ResourceNotFoundException | InvalidArgumentException e) {
            return new ResponseEntity<>(GetRobotResponse.builder().error(new Error(e.getMessage())).build(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @RequestMapping(value = "/{name}/action/move", method = RequestMethod.POST)
    public ResponseEntity<MoveRobotResponse> move(@RequestBody MoveRobotRequest request) {
        try {
            MoveRobotResponse res = robotTrackerService.moveRobot(request);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (ResourceNotFoundException | InvalidArgumentException e) {
            return new ResponseEntity<>(MoveRobotResponse.builder().error(new Error(e.getMessage())).build(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}