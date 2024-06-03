package com.example.contest.dto;

import com.example.contest.dto.ContestTeamResponse;

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
    private boolean jjim;
    private List<ContestTeamResponse> teams;
}
