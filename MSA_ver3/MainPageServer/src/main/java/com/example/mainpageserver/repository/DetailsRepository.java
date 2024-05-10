package com.example.mainpageserver.repository;

import com.example.mainpageserver.entity.DetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailsRepository extends JpaRepository<DetailsEntity, Long> {
}
