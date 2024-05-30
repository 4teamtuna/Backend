package com.example.gsmoa.TeamChatting.repository;

import com.example.gsmoa.TeamChatting.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByContestId(Integer contestId);
}
