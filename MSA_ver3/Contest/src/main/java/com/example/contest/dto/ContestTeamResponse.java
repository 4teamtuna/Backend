package com.example.contest.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContestTeamResponse {
    private Long id;
    private String teamName;

    public Long getId() {
        return id;
    }

    public String getTeamName() {
        return teamName;
    }
}
