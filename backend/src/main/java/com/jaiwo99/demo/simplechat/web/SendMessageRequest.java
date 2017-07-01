package com.jaiwo99.demo.simplechat.web;

import com.jaiwo99.demo.simplechat.domain.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageRequest {

    @NotBlank
    private String author;
    @NotBlank
    private String message;

    public ChatMessage toChatMessage() {
        return new ChatMessage(author, message);
    }
}
