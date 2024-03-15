package com.example.gsmoa.controller;


import com.example.gsmoa.entity.BoardEntity;
import com.example.gsmoa.entity.ChatRoomEntity;
import com.example.gsmoa.entity.ChatRoomMembersEntity;
import com.example.gsmoa.entity.CommentEntity;
import com.example.gsmoa.repository.ChatRoomMembersRepository;
import com.example.gsmoa.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController

public class ChatRoomMembersController {
    @Autowired
    private ChatRoomMembersRepository chatRoomMembersRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    // 채팅방 입장
    @PostMapping("/chat/{chat_room_id}")
    public ChatRoomMembersEntity enterRoom(@PathVariable Long chat_room_id, @RequestBody ChatRoomMembersEntity newMember) {
        ChatRoomEntity room = chatRoomRepository.findById(chat_room_id).orElseThrow(() -> new RuntimeException("Room not found"));
        newMember.setChat_room(room);
        return chatRoomMembersRepository.save(newMember);
    }


}
