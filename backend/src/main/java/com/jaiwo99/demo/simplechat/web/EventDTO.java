package com.jaiwo99.demo.simplechat.web;

import com.jaiwo99.demo.simplechat.domain.Event;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventDTO {

    private String id;
    private String city;
    private String name;
    private LocalDateTime createdDate;

    public EventDTO(Event event) {

        this.id = event.getId();
        this.city = event.getCity();
        this.name = event.getName();
        this.createdDate = event.getCreatedDate();
    }
}
