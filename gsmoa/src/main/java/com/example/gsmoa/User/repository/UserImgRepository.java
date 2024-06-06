package com.example.gsmoa.User.repository;

import com.example.gsmoa.User.entity.UserImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserImgRepository extends JpaRepository<UserImg, Integer> {
}