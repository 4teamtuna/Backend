package com.example.gsmoa.controller;

import com.example.gsmoa.dto.ChatRoom;
import com.example.gsmoa.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
// 채팅방 생성 및 조회를 위한 ChatController 클래스
public class ChatController {

    private final ChatService chatService; // ChatService 객체

    // 채팅방 생성
    // @param name 채팅방 이름
    // @return ChatRoom 채팅방 정보
    @PostMapping
    public ChatRoom createRoom(@RequestParam String name) {
        return chatService.createRoom(name);
    }

    // 채팅방 조회
    // @return List<ChatRoom> 채팅방 목록
    // 채팅방 목록을 조회하는 메소드
    // ChatService의 findAllRoom 메소드를 호출하여 채팅방 목록을 조회
    @GetMapping
    public List<ChatRoom> findAllRoom() {
        return chatService.findAllRoom();
    }
}

