package com.jaiwo99.demo.simplechat.web;

import com.jaiwo99.demo.simplechat.domain.Event;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class EventResponse {

    public EventResponse(List<Event> eventEntities) {
        this.events = eventEntities
                .stream()
                .map(EventDTO::new)
                .collect(Collectors.toList());
    }

    final List<EventDTO> events;
}
