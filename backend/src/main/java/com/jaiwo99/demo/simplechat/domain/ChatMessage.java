package com.jaiwo99.demo.simplechat.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Getter
@EqualsAndHashCode(of = "id")
public class ChatMessage {

    public ChatMessage(String author, String message) {
        this.author = author;
        this.message = message;
    }

    public ChatMessage(String author, String message, LocalDateTime createdDate) {
        this.author = author;
        this.message = message;
        this.createdDate = createdDate;
    }

    private String id = UUID.randomUUID().toString();
    private String author;
    private String message;
    private LocalDateTime createdDate = LocalDateTime.now();
}
