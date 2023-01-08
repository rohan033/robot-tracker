package com.sysdes.rts.application.api.robot.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateHoleRequest {
    private Integer x;
    private Integer y;
}
