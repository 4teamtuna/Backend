package com.example.gsmoa.Contest.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

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

}
