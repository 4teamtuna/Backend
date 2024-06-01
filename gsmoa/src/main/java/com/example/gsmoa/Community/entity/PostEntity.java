package com.example.gsmoa.Community.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

// 게시글 정보를 담는 Entity 클래스
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "post")
// BaseEntity 클래스를 상속받아 생성 시간과 업데이트 시간을 자동으로 관리합니다.
public class PostEntity extends BaseEntity {

    // 게시글 Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long postId;

    // 글 작성자 닉네임
    private String writerId;

    // 제목
    @Column
    private String title;

    // 내용
    @Column(length = 500)
    private String content;

    // 조회수
    @Column
    private int hits;

    // 좋아요
    @Column
    private int likes;

    // 댓글 목록
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<CommentEntity> comments;

    public void increaseHits() {
        this.hits++;
    }

    public void increaseLikes() {
        this.likes++;
    }

    public void decreaseLikes() {
        this.likes--;
    }

    @Transient
    private boolean liked;
}