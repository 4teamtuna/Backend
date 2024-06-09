package com.example.gsmoa.Contest.repository;

import com.example.gsmoa.Contest.entity.Contest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContestRepository extends JpaRepository<Contest, Integer> {
    boolean existsByTitle(String title);
    List<Contest> findByIdBetween(Integer startId, Integer endId);
    List<Contest> findByTag(String tag);

    @Query("SELECT c FROM Contest c WHERE c.tag LIKE %:tag% AND c.period != 0 ORDER BY function('RAND')")
    List<Contest> findByTagsContaining(@Param("tag") String tag);

    @Query("SELECT c FROM Contest c WHERE c.period != 0 ORDER BY function('RAND')")
    List<Contest> findAllHavePeriod();
}