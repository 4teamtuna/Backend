package com.example.gsmoa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "board_table")
public class BoardEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long post_id;

    // 글 작성자
    @Column(length = 20, nullable = false)
    private String writer;

    // 제목
    @Column
    private String title;

    // 내용
    @Column(length = 500)
    private String content;

    // 조회수
    @Column
    private int hits;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CommentEntity> comments;
}