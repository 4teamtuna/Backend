// ChatRoom.java
package com.example.gsmoa.TeamChatting.model;

import com.example.gsmoa.Contest.entity.Contest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



@Entity
@Getter
@Setter
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String teamName;
    private String leader;

    @OneToOne
    @JoinColumn(name = "contest_id")
    private Contest contest;
    private String contestName;

    private String content;
}