package com.sysdes.rts.application.api.robot.ports;

import com.sysdes.rts.application.exception.InvalidArgumentException;
import com.sysdes.rts.application.model.Location;

import java.util.Optional;

public interface GetHoleCommand {
    Optional<Location> getHole(Location request);
}
