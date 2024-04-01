package com.example.gsmoa.service;

import com.example.gsmoa.dto.ChatMessage;
import com.example.gsmoa.dto.ChatRoom;
import com.example.gsmoa.entity.ChatMessageEntity;
import com.example.gsmoa.entity.ChatRoomEntity;
import com.example.gsmoa.repository.ChatMessageRepository;
import com.example.gsmoa.repository.ChatRoomRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
// 채팅방 생성 및 조회를 위한 ChatService 클래스
public class ChatService {

    private final ObjectMapper objectMapper; // ObjectMapper 객체


    private Map<String, ChatRoom> chatRooms; // 채팅방 정보를 담는 Map 객체
    private final ChatRoomRepository chatRoomRepository; // ChatRoomRepository 객체

    @Autowired
    private ChatMessageRepository chatMessageRepository; // ChatMessageRepository 객체

    // ChatService 생성자
    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    // 채팅방 목록을 조회하는 메소드
    public List<ChatRoom> findAllRoom() {
        return new ArrayList<>(chatRooms.values());
    }

    // 채팅방 정보를 조회하는 메소드
    public ChatRoom findRoomById(String roomId) {
        return chatRooms.get(roomId);
    }

    // 채팅방 생성하는 메소드
    public ChatRoom createRoom(String name) {
        String randomId = UUID.randomUUID().toString();
        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(randomId)
                .name(name)
                .build();

        ChatRoomEntity chatRoomEntity = new ChatRoomEntity();
        chatRoomEntity.setRoomId(randomId);
        chatRoomEntity.setName(name);
        chatRoomRepository.save(chatRoomEntity); // 데이터베이스에 저장

        chatRooms.put(randomId, chatRoom); // 메모리에 DTO 저장
        return chatRoom;
    }

    // 채팅방에 메시지를 전송하는 메소드
    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));

            // 메시지 정보를 데이터베이스에 저장
            if (message instanceof ChatMessage) {
                ChatMessage chatMessage = (ChatMessage) message;
                ChatMessageEntity messageEntity = new ChatMessageEntity();

                messageEntity.setType(chatMessage.getType());
                messageEntity.setRoomId(chatMessage.getRoomId());
                messageEntity.setSender(chatMessage.getSender());
                messageEntity.setMessage(chatMessage.getMessage());
                chatMessageRepository.save(messageEntity);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
