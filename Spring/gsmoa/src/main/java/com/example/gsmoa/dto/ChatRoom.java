package com.example.gsmoa.dto;

import com.example.gsmoa.service.ChatService;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;


// 채팅방 정보를 담는 ChatRoom 클래스
@Getter
public class ChatRoom {
    private String roomId; // 방번호
    private String name; // 방이름
    private Set<WebSocketSession> sessions = new HashSet<>(); // 채팅방에 입장한 세션 정보

    // ChatRoom 생성자
    @Builder
    public ChatRoom(String roomId, String name) {
        this.roomId = roomId; // 방번호
        this.name = name; // 방이름
    }

    // 채팅방에 입장한 세션 정보를 추가하는 메소드
    public void handleActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService) {
        if (chatMessage.getType().equals(ChatMessageType.ENTER)) {
            sessions.add(session);
            chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");
        }
        sendMessage(chatMessage, chatService);
    }

    // 채팅방에 메시지를 전송하는 메소드
    public <T> void sendMessage(T message, ChatService chatService) {
        sessions.parallelStream().forEach(session -> chatService.sendMessage(session, message));
    }
}