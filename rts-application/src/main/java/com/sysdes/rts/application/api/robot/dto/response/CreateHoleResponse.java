package com.sysdes.rts.application.api.robot.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateHoleResponse {
    private Integer x;
    private Integer y;
}
