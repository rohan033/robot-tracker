package com.sysdes.rts.application.service;

import com.sysdes.rts.application.api.robot.dto.request.CreateHoleRequest;
import com.sysdes.rts.application.api.robot.dto.response.CreateHoleResponse;
import com.sysdes.rts.application.api.robot.ports.CreateHoleCommand;
import com.sysdes.rts.application.api.robot.ports.GetHoleCommand;
import com.sysdes.rts.application.exception.InvalidArgumentException;
import com.sysdes.rts.application.model.Location;
import com.sysdes.rts.application.spi.repository.RobotTrackerRepository;
import com.sysdes.rts.application.utils.Mapper;
import com.sysdes.rts.application.utils.Validation;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class HoleService implements CreateHoleCommand, GetHoleCommand {

    private RobotTrackerRepository robotTrackerRepository;

    @Override
    public CreateHoleResponse createHole(CreateHoleRequest request) throws InvalidArgumentException {
        Validation.validate(request);
        return Mapper.toCreateHoleResponse(robotTrackerRepository.saveHole(Mapper.toLocation(request)));
    }

    @Override
    public Optional<Location> getHole(Location request) {
        return robotTrackerRepository.findHole(request.getX(), request.getY());
    }
}
