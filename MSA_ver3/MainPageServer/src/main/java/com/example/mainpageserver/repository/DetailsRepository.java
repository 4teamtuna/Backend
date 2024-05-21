package com.example.mainpageserver.repository;

import com.example.mainpageserver.entity.DetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetailsRepository extends JpaRepository<DetailsEntity, Long> {
    List<DetailsEntity> findByTagsContaining(String tag);
}