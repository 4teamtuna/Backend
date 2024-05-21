package com.example.global.repository;

import com.example.global.entity.Contest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContestRepository extends JpaRepository<Contest, Integer> {
    boolean existsByTitle(String title);

}