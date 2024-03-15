package com.example.gsmoa.controller;


import com.example.gsmoa.entity.ChatRoomMembersEntity;
import com.example.gsmoa.repository.ChatRoomMembersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class ChatRoomMembersController {
    @Autowired
    private ChatRoomMembersRepository repository;

    @PostMapping("/enterRoom")
    ChatRoomMembersEntity enterRoom(@RequestBody ChatRoomMembersEntity enterRoom) {
        return repository.save(enterRoom);
    }
}
