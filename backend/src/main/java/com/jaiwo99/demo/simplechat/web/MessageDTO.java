package com.jaiwo99.demo.simplechat.web;

import com.jaiwo99.demo.simplechat.domain.ChatMessage;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MessageDTO {

    public MessageDTO(ChatMessage chatMessage) {
        this.author = chatMessage.getAuthor();
        this.message = chatMessage.getMessage();
        this.createdDate = chatMessage.getCreatedDate();
    }

    private final String author;
    private final String message;
    private final LocalDateTime createdDate;
}
