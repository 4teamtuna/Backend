package com.example.gsmoa.Contest.entity;

import com.example.gsmoa.User.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "contest_jjim")
public class ContestJjim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "contest_id")
    private Contest contest;

    // getters and setters
}