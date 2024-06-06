package com.example.gsmoa.TeamChatting.model;

import com.example.gsmoa.Contest.entity.Contest;
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
    private Long currentMember = 1L;

    @ManyToOne
    @JoinColumn(name = "contest_id")
    private Contest contest;

    private String contestName;

    private String content;

    public void increaseCurrentMember() {
        this.currentMember++;
    }
}