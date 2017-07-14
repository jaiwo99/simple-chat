package com.jaiwo99.demo.simplechat.service;

import com.jaiwo99.demo.simplechat.domain.EventRepo;
import com.jaiwo99.demo.simplechat.web.EventResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EventService {

    private final EventRepo eventRepo;

    public EventResponse findCurrentAvailableEvents() {
        return new EventResponse(eventRepo.findAll()
                .stream()
                .filter(e -> {
                    final LocalDateTime deadline = LocalDateTime.now().minusHours(6);
                    return e.getCreatedDate().isAfter(deadline);
                })
                .collect(Collectors.toList()));
    }
}
