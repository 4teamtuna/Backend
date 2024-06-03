package com.example.contest.dto;
import com.example.contest.entity.Contest;
import jakarta.persistence.*;

import java.util.List;
public class TeamResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String teamName;
    private String leader;
    private Long maxMember;

    @ManyToOne
    @JoinColumn(name = "contest_id")
    private Contest contest;

    private String contestName;

    private String content;
}
