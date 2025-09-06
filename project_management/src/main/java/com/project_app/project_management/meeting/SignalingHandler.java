package com.project_app.project_management.meeting;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SignalingHandler extends TextWebSocketHandler {
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String userId = session.getId();
        sessions.put(userId, session);
        System.out.println("User connected: " + userId);
    }

    private final ObjectMapper mapper = new ObjectMapper();
    private final Map<String, Set<WebSocketSession>> rooms = new ConcurrentHashMap<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode payload = mapper.readTree(message.getPayload());

        String type = payload.has("type") ? payload.get("type").asText() : null;
        String meetId = payload.has("meetId") ? payload.get("meetId").asText() : null;
        System.out.println(meetId);

        if (meetId == null) return;

        rooms.computeIfAbsent(meetId, k -> ConcurrentHashMap.newKeySet()).add(session);

        if ("join".equals(type)) {
            for (WebSocketSession peer : rooms.get(meetId)) {
                if (peer.isOpen() && !peer.getId().equals(session.getId())) {
                    peer.sendMessage(new TextMessage(message.getPayload()));
                }
            }
        } else {
            // forward any other type (offer, answer, candidate) inside this room
            for (WebSocketSession peer : rooms.get(meetId)) {
                if (peer.isOpen() && !peer.getId().equals(session.getId())) {
                    peer.sendMessage(new TextMessage(message.getPayload()));
                }
            }
        }

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) {
        sessions.remove(session.getId());
        System.out.println("User disconnected: " + session.getId());
    }
}