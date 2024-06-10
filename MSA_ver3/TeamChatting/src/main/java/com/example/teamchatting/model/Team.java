package com.example.teamchatting.model;

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
    private Long maxMember;

    private Integer contestId; // Contest ID
    private String contestName; // Contest Name

    private String content;
}