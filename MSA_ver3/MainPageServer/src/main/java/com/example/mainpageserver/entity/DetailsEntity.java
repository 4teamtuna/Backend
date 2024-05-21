package com.example.mainpageserver.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class DetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contest_id;

    //공모전 제목
    private String title;

    //조회수
    private int view_count;

    //개최사
    private String host_name;

    //공모전 사진
    private String imageUrl;

    //공모전 분류
    @ElementCollection
    private List<String> tags;

    //공모전 기간
    private String period;

    //공모전 팀 ID
    private int team_id;

    //공모전 참가자 ID
    private int user_id;



    public String getImageUrl() {
        return this.imageUrl;
    }
}