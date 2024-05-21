package com.example.global.model;

import com.example.global.entity.Contest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String teamName;
    private String leader;

    @ManyToOne
    @JoinColumn(name = "contest_id")
    private Contest contest;

    private String contestName;

    private String content;
}