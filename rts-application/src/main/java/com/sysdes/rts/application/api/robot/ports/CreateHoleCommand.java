package com.sysdes.rts.application.api.robot.ports;


import com.sysdes.rts.application.api.robot.dto.request.CreateHoleRequest;
import com.sysdes.rts.application.api.robot.dto.response.CreateHoleResponse;
import com.sysdes.rts.application.exception.InvalidArgumentException;

public interface CreateHoleCommand {
    CreateHoleResponse createHole(CreateHoleRequest request) throws InvalidArgumentException;
}
