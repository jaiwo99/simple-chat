package com.jaiwo99.demo.simplechat.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Event {

    @Id
    private String id;
    private LocalDateTime createdDate = LocalDateTime.now();
    private String city;
    private String name;

    public Event(String city, String name) {
        this.city = city;
        this.name = name;
    }
}
