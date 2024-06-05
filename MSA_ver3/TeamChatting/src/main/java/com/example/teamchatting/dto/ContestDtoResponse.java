package com.example.teamchatting.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class ContestDtoResponse {
    private Integer id;
    private String title;
    private String hostName;
    private Integer period;
    private Timestamp postedDate;
    private String tag;
    private Integer viewCount;
    private boolean jjim;
    private List<ContestTeamDto> teams;

}

