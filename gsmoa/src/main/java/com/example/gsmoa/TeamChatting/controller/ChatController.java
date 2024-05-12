package com.example.gsmoa.TeamChatting.controller;

import com.example.gsmoa.TeamChatting.model.ChatMessage;
import com.example.gsmoa.TeamChatting.model.Team;
import com.example.gsmoa.TeamChatting.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final TeamRepository teamRepository;

    @Autowired
    public ChatController(SimpMessagingTemplate simpMessagingTemplate, TeamRepository teamRepository) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.teamRepository = teamRepository;
    }

    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        // broadcast message to all subscribers of the room
        simpMessagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

    @PostMapping("/teams")
    public Team createRoom(@RequestBody Team room) {
        return teamRepository.save(room);
    }

    @GetMapping("/teams")
    public List<Team> getRooms() {
        return teamRepository.findAll();
    }

    // Add more methods as needed
}