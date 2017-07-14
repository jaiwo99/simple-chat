package com.jaiwo99.demo.simplechat.web;

import com.jaiwo99.demo.simplechat.domain.EventRepo;
import com.jaiwo99.demo.simplechat.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/events")
public class EventRestController {

    private final EventRepo eventRepo;
    private final EventService eventService;

    @PostMapping
    public void createEvent(@Valid @RequestBody CreateEventDTO dto) {
        eventRepo.save(dto.toEvent());
    }

    @GetMapping
    public EventResponse getEvents() {
        return eventService.findCurrentAvailableEvents();
    }
}
