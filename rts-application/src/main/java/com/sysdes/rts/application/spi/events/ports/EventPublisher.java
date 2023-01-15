package com.sysdes.rts.application.spi.events.ports;

import com.sysdes.rts.application.spi.events.dto.Event;
import com.sysdes.rts.application.spi.events.dto.PublishEventResponse;

import java.util.Optional;

public interface EventPublisher {
    <T> Optional<PublishEventResponse<T>> publishEvent(Event<T> event);
}
