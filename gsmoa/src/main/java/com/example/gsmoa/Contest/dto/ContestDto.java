package com.example.gsmoa.Contest.dto;

import com.example.gsmoa.TeamChatting.dto.ContestTeamDto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class ContestDto {
    private Integer id;
    private String title;
    private String hostName;
    private Integer period;
    private Timestamp postedDate;
    private String tag;
    private Integer viewCount;
    private String details;

    private boolean jjim;
    private List<ContestTeamDto> teams;
}
