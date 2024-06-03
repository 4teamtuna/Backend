package com.example.contest.repository;

import com.example.contest.entity.ContestJjim;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContestJjimRepository extends JpaRepository<ContestJjim, Long> {
    List<ContestJjim> findByUserId(Long userId);

    Optional<ContestJjim> findByUserIdAndContestId(Long userId, Integer contestId);
}