package com.example.gsmoa.TeamChatting.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamResponseDto {
    private Long id;
    private String teamName;
    private String leader;
    private String content;
    private Integer contestId;
    private String contestTitle;
    // Add more contest fields as needed

    // getters and setters
}
