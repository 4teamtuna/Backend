package com.example.gsmoa.Contest.repository;

import com.example.gsmoa.Contest.entity.Contest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContestRepository extends JpaRepository<Contest, Long> {
}