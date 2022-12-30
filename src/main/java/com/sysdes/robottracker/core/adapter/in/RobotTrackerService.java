package com.sysdes.robottracker.core.adapter.in;

import com.sun.javaws.exceptions.InvalidArgumentException;
import com.sysdes.robottracker.core.utils.Mapper;
import com.sysdes.robottracker.core.utils.Validation;
import com.sysdes.robottracker.core.domain.model.Robot;
import com.sysdes.robottracker.core.exception.ResourceNotFoundException;
import com.sysdes.robottracker.core.port.in.dto.*;
import com.sysdes.robottracker.core.port.in.iface.CreateRobotCommand;
import com.sysdes.robottracker.core.port.in.iface.GetRobotCommand;
import com.sysdes.robottracker.core.port.in.iface.MoveRobotCommand;
import com.sysdes.robottracker.core.port.out.iface.RobotTrackerRepository;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class RobotTrackerService implements CreateRobotCommand, GetRobotCommand, MoveRobotCommand {

    private final RobotTrackerRepository robotTrackerRepository;

    @Override
    public CreateRobotResponse createRobot(CreateRobotRequest request) throws InvalidArgumentException {
        Validation.validate(request);
        return Mapper.getCreateResponse(robotTrackerRepository.save(Mapper.toRobot(request)));
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
