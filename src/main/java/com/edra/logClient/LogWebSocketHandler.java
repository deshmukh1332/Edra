package com.edra.logClient;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class LogWebSocketHandler extends TextWebSocketHandler {

    private static final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        System.out.println("Connection established");
        sessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        System.out.println("Received message: " + message.getPayload());
        for (WebSocketSession webSocketSession : sessions) {
            webSocketSession.sendMessage(new TextMessage("Received message: " + message.getPayload()));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) {
        System.out.println("Connection closed");
        sessions.remove(session);
    }

    public static void sendLogToAllSessions() throws IOException {
        for (WebSocketSession webSocketSession : sessions) {
            webSocketSession.sendMessage(new TextMessage(Utils.logQueue.toString()));
        }
    }
}
