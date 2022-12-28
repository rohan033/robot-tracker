package com.sysdes.robottracker.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class Movement {
    private Integer north = 0;
    private Integer east = 0;
    private Integer west = 0;
    private Integer south = 0;
}
