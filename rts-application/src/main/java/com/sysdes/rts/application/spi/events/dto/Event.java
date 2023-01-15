package com.sysdes.rts.application.spi.events.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Event<T> {
    T data;
    LocalDateTime createdAt;
    EventType type;
}
