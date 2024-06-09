package com.example.gsmoa.User.repository;

import com.example.gsmoa.User.entity.Interest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterestRepository extends JpaRepository<Interest, Integer> {
    List<Interest> findByUserId(Integer userId);
}