package com.example.gsmoa.Contest.repository;

import com.example.gsmoa.Contest.entity.Contest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContestRepository extends JpaRepository<Contest, Integer> {
    boolean existsByTitle(String title);
    List<Contest> findByIdBetween(Integer startId, Integer endId);
}