package com.example.gsmoa.repository;

import com.example.gsmoa.entity.BoardEntity;
import com.example.gsmoa.entity.CommentEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    List<BoardEntity> findByTitle(String title);
    List<BoardEntity> findByTitleOrContent(String title, String content);
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    List<CommentEntity> comments = new ArrayList<>();
}
