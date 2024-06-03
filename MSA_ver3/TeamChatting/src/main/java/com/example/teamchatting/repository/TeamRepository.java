package com.example.teamchatting.repository;

import com.example.teamchatting.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByContestId(Integer contestId);
}