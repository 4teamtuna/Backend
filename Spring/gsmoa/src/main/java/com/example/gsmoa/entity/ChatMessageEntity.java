//package com.example.gsmoa.entity;
//
//import jakarta.persistence.*;
//
//import lombok.Getter;
//import lombok.Setter;
//
//@Entity
//@Getter
//@Setter
//@Table(name = "chat_message")
//public class ChatMessageEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne // 여러 메시지가 하나의 채팅방에 속할 수 있음을 나타냅니다.
//    @JoinColumn(name = "chat_room_id") // 외래 키의 이름을 지정합니다.
//    private ChatRoomEntity chatRoom; // 연관된 채팅방 엔티티
//
//    @Enumerated(EnumType.STRING)
//    private MessageType type;
//
//    private String content;
//    private String sender;
//
//    public enum MessageType {
//        CHAT,
//        JOIN,
//        LEAVE
//    }
//}
