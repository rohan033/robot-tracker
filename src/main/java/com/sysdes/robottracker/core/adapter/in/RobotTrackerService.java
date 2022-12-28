package com.sysdes.robottracker.core.adapter.in;

import com.sun.javaws.exceptions.InvalidArgumentException;
import com.sysdes.robottracker.common.dto.Location;
import com.sysdes.robottracker.common.enums.RobotStatus;
import com.sysdes.robottracker.common.utils.Validation;
import com.sysdes.robottracker.core.domain.model.Robot;
import com.sysdes.robottracker.core.port.in.dto.*;
import com.sysdes.robottracker.core.port.in.iface.CreateRobotCommand;
import com.sysdes.robottracker.core.port.in.iface.GetRobotCommand;
import com.sysdes.robottracker.core.port.in.iface.MoveRobotCommand;
import com.sysdes.robottracker.core.port.out.iface.RobotRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RobotTrackerService implements CreateRobotCommand, GetRobotCommand, MoveRobotCommand {

    private final RobotRepository robotRepository;

    @Override
    public CreateRobotResponse createRobot(CreateRobotRequest request) throws InvalidArgumentException {
        Validation.validate(request);
        return new CreateRobotResponse(request.getName(), request.getLocation(), RobotStatus.ALIVE);
    }

    @Override
    public GetRobotResponse getRobot(GetRobotRequest request) throws InvalidArgumentException {
        Validation.validate(request);
        return new GetRobotResponse(request.getName(), new Location(0,0), RobotStatus.ALIVE);
    }

    @Override
    public MoveRobotResponse moveRobot(MoveRobotRequest request) throws InvalidArgumentException {
        Validation.validate(request);
        Robot robot = robotRepository.findRobotByName(request.getName());
        Robot movedRobot = robot.move(request.getMovement());
        return new MoveRobotResponse(
                request.getName(),
                robot.getCurrentLocation(),
                movedRobot.getCurrentLocation(),
                request.getMovement()
        );
    }
}
