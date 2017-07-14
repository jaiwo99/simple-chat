package com.jaiwo99.demo.simplechat.service;

import com.jaiwo99.demo.simplechat.domain.Event;
import com.jaiwo99.demo.simplechat.domain.EventRepo;
import com.jaiwo99.demo.simplechat.web.EventDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EventServiceTest {

    @InjectMocks
    private EventService eventService;
    @Mock
    private EventRepo eventRepo;
    private java.util.List<Event> eventList;

    @Before
    public void setup() {
        final Event event1 = new Event("city1", "name1");
        event1.setCreatedDate(LocalDateTime.now().minusHours(7));

        final Event event2 = new Event("city2", "name2");

        eventList = Arrays.asList(event1, event2);
        when(eventRepo.findAll()).thenReturn(eventList);
    }

    @Test
    public void findCurrentAvailableEvents_should_find_events_within_6_hours() {
        List<EventDTO> events = eventService.findCurrentAvailableEvents().getEvents();
        assertThat(events).hasSize(1);
        assertThat(events.get(0).getCity()).isEqualTo("city2");
    }

    @Test
    public void findCurrentAvailableEvents_should_NOT_find_events_outside_6_hours() {
        List<EventDTO> events = eventService.findCurrentAvailableEvents().getEvents();
        assertThat(events).hasSize(1);

        events.forEach(e -> assertThat(e.getCity()).isNotEqualTo("city1"));
    }
}