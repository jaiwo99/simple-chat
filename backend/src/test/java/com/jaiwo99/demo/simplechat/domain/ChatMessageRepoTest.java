package com.jaiwo99.demo.simplechat.domain;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ChatMessageRepoTest {

    private ChatMessageRepo repo;
    private ChatMessage msg1;
    private ChatMessage msg2;
    private ChatMessage msg3;

    @Before
    public void setup() {
        repo = new ChatMessageRepo();
        final LocalDateTime now = LocalDateTime.now();
        msg1 = new ChatMessage("author1", "message1", now.minusSeconds(2));
        msg2 = new ChatMessage("author2", "message2", now.minusSeconds(1));
        msg3 = new ChatMessage("author3", "message3", now);
    }

    @Test
    public void add_should_add_chatmessage() {
        repo.add(msg1);
        repo.add(msg2);
        repo.add(msg3);

        final List<ChatMessage> list = repo.fetch();
        assertThat(list).hasSize(3);
    }

    @Test
    public void add_should_ignore_existing_message() {
        repo.add(msg1);
        repo.add(msg1);
        repo.add(msg1);
        repo.add(msg1);

        final List<ChatMessage> list = repo.fetch();
        assertThat(list).hasSize(1);
    }

    @Test
    public void add_should_return_message() {
        assertThat(repo.add(msg1).isPresent()).isTrue();
        assertThat(repo.add(msg1).isPresent()).isFalse();
    }

    @Test
    public void fetch_should_fetch_messages_sorted() {
        repo.add(msg3);
        repo.add(msg1);
        repo.add(msg2);

        final List<ChatMessage> list = repo.fetch();
        assertThat(list).hasSize(3);
        assertThat(list.get(0)).isEqualToComparingFieldByField(msg1);
        assertThat(list.get(1)).isEqualToComparingFieldByField(msg2);
        assertThat(list.get(2)).isEqualToComparingFieldByField(msg3);
    }

    @Test
    public void clear_should_clear_list() {
        repo.add(msg3);
        repo.add(msg1);
        repo.add(msg2);

        repo.clear();

        assertThat(repo.fetch()).hasSize(0);
    }
}