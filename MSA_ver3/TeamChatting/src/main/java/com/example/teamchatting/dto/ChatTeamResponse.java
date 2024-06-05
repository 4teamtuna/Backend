package com.example.teamchatting.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatTeamResponse {
    private Long id;
    private String teamName;
    private String leader;
    private Long maxMember;
    private String content;
}