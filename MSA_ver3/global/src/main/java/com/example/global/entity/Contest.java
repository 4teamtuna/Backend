package com.example.global.entity;

import com.example.global.model.Team;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "Contest")
@Getter
@Setter
@ToString
public class Contest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contest_id")
    private Integer contest_id;

    @Column(nullable = false, length = 255, unique = true)
    private String title;

    @Column(name = "host_name", length = 100)
    private String hostName;

    @Column
    private Integer period;

    @Column(name = "posted_date")
    private Timestamp postedDate;

    @Column(length = 100)
    private String tag;

    @Lob
    @Convert(converter = BlobConverter.class)
    @Column
    private byte[] image;

    @Column(name = "view_count")
    private Integer viewCount = 0;

    @OneToMany(mappedBy = "contest")
    private List<Team> teams;
}