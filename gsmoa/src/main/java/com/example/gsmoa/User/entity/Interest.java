package com.example.gsmoa.User.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "interest")
public class Interest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String interest;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
