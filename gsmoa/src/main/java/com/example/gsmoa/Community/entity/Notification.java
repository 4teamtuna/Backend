package com.example.gsmoa.Community.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long postOwnerId;

    @Column
    private Long postId;

    @Column
    private Long commentId;

    @Column
    private boolean checked;

}
