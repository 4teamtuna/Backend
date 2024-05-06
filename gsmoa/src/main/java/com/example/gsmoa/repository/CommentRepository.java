package com.example.gsmoa.repository;

import com.example.gsmoa.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository 인터페이스를 상속받아 DB에 접근하는 메서드를 사용할 수 있습니다.
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}
