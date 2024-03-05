package com.example.gsmoa.repository;

import com.example.gsmoa.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    List<BoardEntity> findByTitle(String title);
    List<BoardEntity> findByTitleOrContent(String title, String content);
}
