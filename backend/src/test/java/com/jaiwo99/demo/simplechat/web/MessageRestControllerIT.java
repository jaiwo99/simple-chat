package com.jaiwo99.demo.simplechat.web;

import com.jaiwo99.demo.simplechat.AbstractIT;
import com.jaiwo99.demo.simplechat.domain.ChatMessage;
import com.jaiwo99.demo.simplechat.domain.ChatMessageRepo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MessageRestControllerIT extends AbstractIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ChatMessageRepo repo;

    @Before
    public void setup() {
        repo.clear();
        repo.add(new ChatMessage("author1", "message1"));
        repo.add(new ChatMessage("author2", "message2"));
    }

    @Test
    public void getMessages_should_return_all_messages() throws Exception {
        mockMvc.perform(get("/api/messages"))
                .andExpect(jsonPath("$.messages", hasSize(2)))
                .andExpect(status().isOk());
    }

    @Test
    public void addMessages_should_validate_message() throws Exception {
        final SendMessageRequest messageRequest = new SendMessageRequest("", "message");
        mockMvc.perform(post("/api/messages")
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonString(messageRequest))
        ).andExpect(status().is(400));
    }

    @Test
    public void addMessages_should_add_message() throws Exception {
        final SendMessageRequest messageRequest = new SendMessageRequest("test", "123");
        mockMvc.perform(post("/api/messages")
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonString(messageRequest))
        ).andExpect(status().is(200));

        final List<ChatMessage> result = repo.fetch();
        assertThat(result).hasSize(3);
        assertThat(result.get(2).getAuthor()).isEqualTo(messageRequest.getAuthor());
        assertThat(result.get(2).getMessage()).isEqualTo(messageRequest.getMessage());
    }
}