package com.example.gsmoa.controller;

import com.example.gsmoa.entity.ChatRoomEntity;
import com.example.gsmoa.repository.ChatRoomRepository;
import jakarta.persistence.Column;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/chat")
public class ChatRoomController {
    @Autowired
    private ChatRoomRepository repository;

    @PostMapping("/newRoom")
    ChatRoomEntity newRoom(@RequestBody ChatRoomEntity newRoom) {
        return repository.save(newRoom);
    }
}

