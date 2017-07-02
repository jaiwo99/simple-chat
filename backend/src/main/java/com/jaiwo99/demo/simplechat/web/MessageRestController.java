package com.jaiwo99.demo.simplechat.web;

import com.jaiwo99.demo.simplechat.domain.ChatMessageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageRestController {

    private final ChatMessageRepo repo;
    private final SimpMessagingTemplate template;

    @GetMapping
    public MessageResponse getMessages() {
        return new MessageResponse(repo.fetch());
    }

    @PostMapping
    public void addMessage(@Valid @RequestBody SendMessageRequest message) {
        repo.add(message.toChatMessage())
                .ifPresent(msg -> template.convertAndSend("/topic/chat", new MessageDTO(msg)));
    }
}
