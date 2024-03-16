//package com.example.gsmoa.repository;
//
//import com.example.gsmoa.entity.ChatRoomEntity;
//import com.example.gsmoa.entity.ChatRoomMembersEntity;
//import com.example.gsmoa.entity.CommentEntity;
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.OneToMany;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public interface ChatRoomMembersRepository extends JpaRepository<ChatRoomMembersEntity, Long> {
//    @OneToMany(mappedBy = "chat_room", cascade = CascadeType.ALL, orphanRemoval = true)
//    List<ChatRoomMembersEntity> chat_room_members = new ArrayList<>();
//}
//
