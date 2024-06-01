package com.example.gsmoa.Community.entity;

import com.example.gsmoa.User.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "comment")
// BaseEntity 클래스를 상속받아 생성 시간과 업데이트 시간을 자동으로 관리합니다.
public class CommentEntity extends BaseEntity {

    // 댓글 Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    // 게시글 Id
    // 게시글과 댓글은 다대일 관계이므로 게시글과 댓글을 연결하는 외래키를 사용합니다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    @JsonBackReference
    private PostEntity post;

    // 댓글 작성자 Id
    private String writerId;

    // 댓글 내용
    @Column(length = 500)
    private String content;

    @ManyToOne
    @JoinColumn(name = "post_owner_id")
    private UserEntity postOwner;

}