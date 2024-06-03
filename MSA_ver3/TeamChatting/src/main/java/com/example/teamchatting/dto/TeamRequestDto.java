package com.example.teamchatting.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamRequestDto {
    private String teamName;
    private String leader;
    private String content;
    private Integer contestId;
    private Long maxMember;

}