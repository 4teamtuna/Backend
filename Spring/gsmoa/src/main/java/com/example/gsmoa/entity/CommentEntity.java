package com.example.gsmoa.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "comment_table")
public class CommentEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @Column
    private int post_num;

    // 글 작성자
    @Column(length = 20, nullable = false)
    private String writer;

    // 내용
    @Column(length = 500)
    private String content;

}