package com.jaiwo99.demo.simplechat.web;

import com.jaiwo99.demo.simplechat.domain.ChatMessage;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MessageResponse {

    public MessageResponse(List<ChatMessage> messages) {
        this.messages = messages
                .stream()
                .map(MessageDTO::new)
                .collect(Collectors.toList());
    }

    final List<MessageDTO> messages;
}
