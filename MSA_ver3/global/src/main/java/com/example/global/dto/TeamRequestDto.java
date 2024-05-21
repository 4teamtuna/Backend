package com.example.global.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamRequestDto {
    private String teamName;
    private String leader;
    private String content;
    private Integer contestId;

}