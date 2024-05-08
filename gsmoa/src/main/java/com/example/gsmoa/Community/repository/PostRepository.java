package com.example.gsmoa.Community.repository;

import com.example.gsmoa.Community.entity.PostEntity;
import com.example.gsmoa.Community.entity.CommentEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

// JpaRepository 인터페이스를 상속받아 DB에 접근하는 메서드를 사용할 수 있습니다.
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    // 게시글 제목으로 검색
    List<PostEntity> findByTitle(String title);

    // 게시글 내용으로 검색
    List<PostEntity> findByTitleOrContent(String title, String content);

    // 게시글 Id로 검색
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    List<CommentEntity> comments = new ArrayList<>(); // 댓글 목록을 담을 리스트
}
