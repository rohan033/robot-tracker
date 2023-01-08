package com.sysdes.rts.application.utils;

import com.sysdes.rts.application.api.robot.dto.request.CreateHoleRequest;
import com.sysdes.rts.application.exception.InvalidArgumentException;
import com.sysdes.rts.application.api.robot.dto.request.CreateRobotRequest;
import com.sysdes.rts.application.api.robot.dto.request.GetRobotRequest;
import com.sysdes.rts.application.api.robot.dto.request.MoveRobotRequest;
import com.sysdes.rts.application.model.Location;

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

    public static void validate(CreateHoleRequest request) throws InvalidArgumentException {
        notNull(request, "Request cannot be null");
        notNull(request.getX(), "X coordinate for Hole cannot be null");
        notNull(request.getY(), "Y coordinate for Hole cannot be null");
    }
}
