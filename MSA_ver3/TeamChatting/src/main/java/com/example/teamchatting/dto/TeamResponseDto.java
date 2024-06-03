package com.example.teamchatting.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamResponseDto {
    private Long id;
    private String teamName;
    private String leader;
    private String content;
    private byte[] contestImage;
    private Integer contestId;
    private int sessionCount;
    private String contestTitle;
    // Add more contest fields as needed

    // getters and setters
}