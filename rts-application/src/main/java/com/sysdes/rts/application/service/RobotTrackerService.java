package com.sysdes.rts.application.service;


import com.sysdes.rts.application.api.robot.dto.request.CreateRobotRequest;
import com.sysdes.rts.application.api.robot.dto.request.GetRobotRequest;
import com.sysdes.rts.application.api.robot.dto.request.MoveRobotRequest;
import com.sysdes.rts.application.api.robot.dto.response.CreateRobotResponse;
import com.sysdes.rts.application.api.robot.dto.response.GetRobotResponse;
import com.sysdes.rts.application.api.robot.dto.response.MoveRobotResponse;
import com.sysdes.rts.application.enums.RobotStatus;
import com.sysdes.rts.application.exception.IllegalStateException;
import com.sysdes.rts.application.exception.InvalidArgumentException;
import com.sysdes.rts.application.exception.ResourceAlreadyExistsException;
import com.sysdes.rts.application.exception.ResourceNotFoundException;
import com.sysdes.rts.application.model.Location;
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
    private final HoleService holeService;

    @Override
    public CreateRobotResponse createRobot(CreateRobotRequest request) throws InvalidArgumentException, ResourceAlreadyExistsException {
        Validation.validate(request);
        final Optional<Robot> robot = robotTrackerRepository.findRobotByName(request.getName());
        if(robot.isPresent())
            throw new ResourceAlreadyExistsException("Robot with name " + request.getName() + " already exists");

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
    public MoveRobotResponse moveRobot(final MoveRobotRequest request) throws InvalidArgumentException, ResourceNotFoundException, IllegalStateException {
        Validation.validate(request);
        final Optional<Robot> robot = robotTrackerRepository.findRobotByName(request.getName());
        if(!robot.isPresent())
            throw new ResourceNotFoundException("Robot doesn't exist with name " + request.getName());

        Optional<Robot> alive = robot.filter(r -> RobotStatus.ALIVE.equals(r.getStatus()));
        if(!alive.isPresent())
            throw new IllegalStateException("Robot " + request.getName() + " is already dead and cannot be moved");

        return alive.map(r -> {
            Location newLocation = r.getCurrentLocation().applyMovement(request.getMovement());
            Optional<Location> hole = holeService.getHole(newLocation);
            Robot movedRobot = r.move(request.getMovement(), hole.isPresent());
            robotTrackerRepository.save(movedRobot);
            return Mapper.getMoveResponse(request, r, movedRobot);
        }).get();
    }

}
