package com.jaiwo99.demo.simplechat.web;

import com.jaiwo99.demo.simplechat.domain.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEventDTO {

    @NotBlank
    private String city;
    @NotBlank
    private String name;

    public Event toEvent() {
        return new Event(city, name);
    }
}
