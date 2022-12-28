package com.sysdes.robottracker.common.utils;

import com.sun.javaws.exceptions.InvalidArgumentException;
import com.sysdes.robottracker.core.port.in.dto.CreateRobotRequest;
import com.sysdes.robottracker.core.port.in.dto.GetRobotRequest;
import com.sysdes.robottracker.core.port.in.dto.MoveRobotRequest;

public class Validation {
    private static void notNull(Object obj, String message) throws InvalidArgumentException {
        if (obj != null) return;
        throw new InvalidArgumentException(new String[]{message});
    }

    public static void validate(CreateRobotRequest request) throws InvalidArgumentException {
        notNull(request, "Request cannot be null");
        notNull(request.getName(), "Robot name cannot be null");
        notNull(request.getLocation(), "Robot location cannot be null");
    }

    public static void validate(GetRobotRequest request) throws InvalidArgumentException {
        notNull(request, "Request cannot be null");
        notNull(request.getName(), "Robot name cannot be null");
    }

    public static void validate(MoveRobotRequest request) throws InvalidArgumentException {
        notNull(request, "Request cannot be null");
        notNull(request.getName(), "Robot name cannot be null");
        notNull(request.getMovement(), "Movement cannot be null");
    }
}
