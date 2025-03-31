package com.th.hair.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.th.hair.chat.model.ChatDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private static final Map<String, List<WebSocketSession>> chatRooms = new ConcurrentHashMap<>();

    private final ObjectMapper objectMapper;

    public ChatWebSocketHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 사용자가 웹소켓 연결을 맺으면 호출됨
        String chatRoomId = getChatRoomId(session);
        log.info("웹소켓 연결 시도");
        chatRooms.computeIfAbsent(chatRoomId, k -> new CopyOnWriteArrayList<>()).add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 메시지를 받았을 때 호출됨
        ChatDto chatMessage = objectMapper.readValue(message.getPayload(), ChatDto.class);
        String ichat = chatMessage.getIchat().toString();
        log.info("web socket - ichat : {}", ichat);

        List<WebSocketSession> sessions = chatRooms.getOrDefault(ichat, Collections.emptyList());
        for (WebSocketSession s : sessions) {
            if (s.isOpen()) {
                s.sendMessage(new TextMessage(objectMapper.writeValueAsString(chatMessage)));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 사용자가 연결을 종료하면 호출됨
        String chatRoomId = getChatRoomId(session);
        chatRooms.getOrDefault(chatRoomId, new ArrayList<>()).remove(session);
    }

    private String getChatRoomId(WebSocketSession session) {
        log.info("세션 uri : {}", session.getUri());
        return session.getUri().getQuery().split("=")[1]; // 예: ws://localhost:8080/ws/chat?chatRoomId=1
    }
}
