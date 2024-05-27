package com.example.gsmoa.Contest.service;

import com.example.gsmoa.Contest.entity.ContestJjim;
import com.example.gsmoa.Contest.repository.ContestJjimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContestJjimService {

    private final ContestJjimRepository contestJjimRepository;

    @Autowired
    public ContestJjimService(ContestJjimRepository contestJjimRepository) {
        this.contestJjimRepository = contestJjimRepository;
    }

    public ContestJjim addLike(ContestJjim contestJjim) {
        return contestJjimRepository.save(contestJjim);
    }

    public void removeLike(Long id) {
        contestJjimRepository.deleteById(id);
    }

    public List<ContestJjim> getLikedContests(Long userId) {
        return contestJjimRepository.findByUserId(userId);
    }
}