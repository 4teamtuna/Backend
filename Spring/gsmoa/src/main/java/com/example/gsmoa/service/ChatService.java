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
public class ChatService {

    private final ObjectMapper objectMapper;


    private Map<String, ChatRoom> chatRooms;
    private final ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;






    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    public List<ChatRoom> findAllRoom() {
        return new ArrayList<>(chatRooms.values());
    }

    public ChatRoom findRoomById(String roomId) {
        return chatRooms.get(roomId);
    }

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
