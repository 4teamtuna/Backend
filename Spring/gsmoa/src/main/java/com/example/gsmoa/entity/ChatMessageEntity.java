package com.example.gsmoa.entity;
import com.example.gsmoa.dto.ChatMessageType;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



@Entity
@Getter
@Setter
@Table(name = "chat_message")
public class ChatMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ChatMessageType type; // 메시지 타입

    private String roomId; // 방번호
    private String sender; // 메시지 보낸 사람
    private String message; // 메시지


}
