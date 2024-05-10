package com.example.gsmoa.Contest.service;

import com.example.gsmoa.Contest.entity.Contest;
import com.example.gsmoa.Contest.repository.ContestRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContestService {
    @Autowired
    private ContestRepository contestRepository;

    public Contest saveContest(Contest contest) {
        return contestRepository.save(contest);
    }

    //@Transactional
    public List<Contest> saveAllContests(List<Contest> contests) {
        return contestRepository.saveAll(contests);
    }
}
