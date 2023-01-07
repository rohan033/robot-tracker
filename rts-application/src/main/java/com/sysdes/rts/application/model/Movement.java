package com.sysdes.rts.application.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder(toBuilder = true)
public class Movement {
    private Integer north = 0;
    private Integer east = 0;
    private Integer west = 0;
    private Integer south = 0;
}
