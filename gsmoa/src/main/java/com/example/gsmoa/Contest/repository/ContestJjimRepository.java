package com.example.gsmoa.Contest.repository;

import com.example.gsmoa.Contest.entity.ContestJjim;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContestJjimRepository extends JpaRepository<ContestJjim, Long> {
    List<ContestJjim> findByUserId(Long userId);
}