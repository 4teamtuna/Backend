package com.example.gsmoa.entity;
import com.example.gsmoa.dto.ChatMessageType;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


// 채팅 메시지 정보를 저장하는 ChatMessageEntity 클래스
@Entity
@Getter
@Setter
@Table(name = "chat_message")
public class ChatMessageEntity {
    // 채팅 메시지 Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 메시지 타입
    @Enumerated(EnumType.STRING)
    private ChatMessageType type; // 메시지 타입

    private String roomId; // 방번호
    private String sender; // 메시지 보낸 사람
    private String message; // 메시지


}
