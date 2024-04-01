package com.example.gsmoa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// 채팅방 정보를 저장하는 ChatRoomEntity 클래스
@Entity
@Getter
@Setter
@Table(name = "chat_room")
public class ChatRoomEntity {

    // 채팅방 Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomId; // 방번호
    private String name; // 방이름
}
