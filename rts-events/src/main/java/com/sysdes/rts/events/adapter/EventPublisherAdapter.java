package com.sysdes.rts.events.adapter;

import com.sysdes.rts.application.spi.events.dto.Event;
import com.sysdes.rts.application.spi.events.dto.PublishEventResponse;
import com.sysdes.rts.application.spi.events.ports.EventPublisher;
import com.sysdes.rts.events.util.Serdes;

import java.util.Optional;

public class EventPublisherAdapter implements EventPublisher {

    @Override
    public <T> Optional<PublishEventResponse<T>> publishEvent(Event<T> event) {
        return Serdes.serialize(event)
                .map(this::produce);
    }

    private <T> PublishEventResponse<T> produce(String data) {
        return null;
    }


}
