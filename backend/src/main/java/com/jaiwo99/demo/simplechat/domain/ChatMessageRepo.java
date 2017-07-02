package com.jaiwo99.demo.simplechat.domain;

import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Component
public class ChatMessageRepo {

    // only eligible for demo
    private final List<ChatMessage> list = new CopyOnWriteArrayList<>();

    public Optional<ChatMessage> add(ChatMessage chatMessage) {
        if (!list.contains(chatMessage)) {
            list.add(chatMessage);
            return Optional.of(chatMessage);
        }

        return Optional.empty();
    }

    public List<ChatMessage> fetch() {
        return list.stream().sorted(COMPARATOR).collect(Collectors.toList());
    }

    public void clear() {
        list.clear();
    }

    private static final Comparator<ChatMessage> COMPARATOR =
            (a, b) -> a.getCreatedDate().isBefore(b.getCreatedDate()) ? -1 : 1;
}
