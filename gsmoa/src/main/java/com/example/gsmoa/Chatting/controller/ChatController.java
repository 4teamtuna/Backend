package com.example.gsmoa.Chatting.controller;

import com.example.gsmoa.Chatting.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public ChatController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        // broadcast message to all subscribers of the room
        simpMessagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}
