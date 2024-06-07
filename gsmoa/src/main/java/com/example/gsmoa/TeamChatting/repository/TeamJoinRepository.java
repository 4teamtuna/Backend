package com.example.gsmoa.TeamChatting.repository;

import com.example.gsmoa.TeamChatting.model.TeamJoin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamJoinRepository extends JpaRepository<TeamJoin, Long> {
    List<TeamJoin> findByUserId(Long userId);
    TeamJoin findByUserIdAndTeamId(Long teamId, Long userId);
}
