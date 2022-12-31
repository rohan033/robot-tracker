package com.sysdes.rts.application.service;


import com.sysdes.rts.application.api.robot.dto.request.CreateRobotRequest;
import com.sysdes.rts.application.api.robot.dto.request.GetRobotRequest;
import com.sysdes.rts.application.api.robot.dto.request.MoveRobotRequest;
import com.sysdes.rts.application.api.robot.dto.response.CreateRobotResponse;
import com.sysdes.rts.application.api.robot.dto.response.GetRobotResponse;
import com.sysdes.rts.application.api.robot.dto.response.MoveRobotResponse;
import com.sysdes.rts.application.exception.InvalidArgumentException;
import com.sysdes.rts.application.exception.ResourceNotFoundException;
import com.sysdes.rts.application.model.Robot;
import com.sysdes.rts.application.api.robot.ports.CreateRobotCommand;
import com.sysdes.rts.application.api.robot.ports.GetRobotCommand;
import com.sysdes.rts.application.api.robot.ports.MoveRobotCommand;
import com.sysdes.rts.application.spi.repository.RobotTrackerRepository;
import com.sysdes.rts.application.utils.Mapper;
import com.sysdes.rts.application.utils.Validation;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class RobotTrackerService implements CreateRobotCommand, GetRobotCommand, MoveRobotCommand {

    private final RobotTrackerRepository robotTrackerRepository;

    @Override
    public CreateRobotResponse createRobot(CreateRobotRequest request) throws InvalidArgumentException {
        Validation.validate(request);
        return Mapper.getCreateResponse(robotTrackerRepository.save(Mapper.toNewRobot(request)));
    }

    @Override
    public GetRobotResponse getRobot(GetRobotRequest request) throws InvalidArgumentException, ResourceNotFoundException {
        Validation.validate(request);
        final Optional<Robot> robot = robotTrackerRepository.findRobotByName(request.getName());
        return robot.map(Mapper::getGetResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Robot doesn't exist with name " + request.getName()));
    }

    @Override
    public MoveRobotResponse moveRobot(final MoveRobotRequest request) throws InvalidArgumentException, ResourceNotFoundException {
        Validation.validate(request);
        final Optional<Robot> robot = robotTrackerRepository.findRobotByName(request.getName());
        return robot.map(r -> {
            Robot movedRobot = r.move(request.getMovement());
            robotTrackerRepository.save(movedRobot);
            return Mapper.getMoveResponse(request, r, movedRobot);
        }).orElseThrow(() -> new ResourceNotFoundException("Robot doesn't exist with name " + request.getName()));
    }

}
