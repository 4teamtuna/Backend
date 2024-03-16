package com.example.gsmoa.repository;

import com.example.gsmoa.dto.ChatMessage;
import com.example.gsmoa.entity.ChatMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> {
}
