package com.example.gsmoa.Community.repository;

import com.example.gsmoa.Community.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findAllByPost_PostId(Long postId);
}