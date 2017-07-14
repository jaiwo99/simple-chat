package com.jaiwo99.demo.simplechat.config;

import com.jaiwo99.demo.simplechat.AbstractIT;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import static java.util.Collections.singletonList;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;

@Ignore
public class WebSocketConfigurationIT extends AbstractIT {

    private BlockingQueue<String> blockingQueue;
    private WebSocketStompClient stompClient;

    @LocalServerPort
    int port;

    @Before
    public void setup() {
        blockingQueue = new LinkedBlockingDeque<>();
        stompClient = new WebSocketStompClient(new SockJsClient(
                singletonList(new WebSocketTransport(new StandardWebSocketClient()))));
    }

    @Test
    public void message_can_be_received_from_server() throws Exception {
        final StompSession session = stompClient
                .connect("ws://localhost:"+String.valueOf(port)+"/apiws/chat", new StompSessionHandlerAdapter() {
                })
                .get(1, SECONDS);
        session.subscribe("/topic/chat", new DefaultStompFrameHandler());

        final String message = "hello world";
        session.send("/topic/chat", message.getBytes());

        assertThat(blockingQueue.poll(1, SECONDS)).isEqualTo(message);
    }

    class DefaultStompFrameHandler implements StompFrameHandler {
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            return byte[].class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            blockingQueue.offer(new String((byte[]) o));
        }
    }
}
