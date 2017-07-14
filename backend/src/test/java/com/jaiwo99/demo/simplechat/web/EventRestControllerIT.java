package com.jaiwo99.demo.simplechat.web;

import com.jaiwo99.demo.simplechat.AbstractIT;
import com.jaiwo99.demo.simplechat.domain.Event;
import com.jaiwo99.demo.simplechat.domain.EventRepo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EventRestControllerIT extends AbstractIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EventRepo eventRepo;

    @Before
    public void setup() {
        eventRepo.deleteAll();
    }

    @Test
    public void event_should_be_able_to_created() throws Exception {
        final CreateEventDTO messageRequest = new CreateEventDTO("city", "name");
        mockMvc.perform(post("/api/events")
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonString(messageRequest))
        ).andExpect(status().is(200));

        assertThat(eventRepo.count()).isEqualTo(1);
    }

    @Test
    public void event_should_able_to_be_read() throws Exception {

        eventRepo.save(new Event("city1", "name1"));
        eventRepo.save(new Event("city2", "name2"));
        mockMvc.perform(get("/api/events"))
                .andExpect(jsonPath("$.events", hasSize(2)))
                .andExpect(jsonPath("$.events[*].city", containsInAnyOrder("city1", "city2")))
                .andExpect(jsonPath("$.events[*].name", containsInAnyOrder("name1", "name2")))
                .andExpect(status().isOk());
    }

    @Test
    public void only_events_within_6_hours_can_be_found() throws Exception {

        eventRepo.save(new Event("city1", "name1"));
        eventRepo.save(new Event("city2", "name2"));

        final Event outdatedEvent = new Event("city2", "name2");
        outdatedEvent.setCreatedDate(LocalDateTime.now().minusHours(7));
        eventRepo.save(outdatedEvent);

        mockMvc.perform(get("/api/events"))
                .andExpect(jsonPath("$.events", hasSize(2)))
                .andExpect(jsonPath("$.events[*].city", containsInAnyOrder("city1", "city2")))
                .andExpect(jsonPath("$.events[*].name", containsInAnyOrder("name1", "name2")))
                .andExpect(status().isOk());
    }
}