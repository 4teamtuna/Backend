package com.example.gsmoa.User.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "UserImg")
public class UserImg {

    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserEntity userId;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] img;
}