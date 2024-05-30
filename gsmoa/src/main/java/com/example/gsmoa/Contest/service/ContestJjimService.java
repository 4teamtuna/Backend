package com.example.gsmoa.Contest.service;

import com.example.gsmoa.Contest.dto.ContestDto;
import com.example.gsmoa.Contest.entity.Contest;
import com.example.gsmoa.Contest.entity.ContestJjim;
import com.example.gsmoa.Contest.repository.ContestJjimRepository;
import com.example.gsmoa.Contest.repository.ContestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContestJjimService {

    private final ContestJjimRepository contestJjimRepository;

    @Autowired
    private ContestRepository contestRepository;

    @Autowired
    public ContestJjimService(ContestJjimRepository contestJjimRepository) {
        this.contestJjimRepository = contestJjimRepository;
    }

    public ContestJjim saveContestJjim(ContestJjim contestJjim) {
        return contestJjimRepository.save(contestJjim);
    }

    public void removeLikedContest(Long userId, Integer contestId) {
        ContestJjim contestJjim = contestJjimRepository.findByUserIdAndContestId(userId, contestId)
                .orElseThrow(() -> new IllegalArgumentException("No ContestJjim found with userId: " + userId + " and contestId: " + contestId));
        contestJjimRepository.delete(contestJjim);
    }

    public List<ContestJjim> getLikedContests(Long userId) {
        return contestJjimRepository.findByUserId(userId);
    }

    public List<ContestDto> getLikedContestsDetails(Long userId) {
        List<ContestJjim> likedContests = contestJjimRepository.findByUserId(userId);
        return likedContests.stream()
                .map(contestJjim -> {
                    Contest contest = contestRepository.findById(contestJjim.getContestId().intValue())
                            .orElseThrow(() -> new IllegalArgumentException("Invalid contest ID: " + contestJjim.getContestId()));
                    ContestDto contestDto = new ContestDto();
                    contestDto.setId(contest.getId());
                    contestDto.setTitle(contest.getTitle());
                    contestDto.setHostName(contest.getHostName());
                    contestDto.setPeriod(contest.getPeriod());
                    contestDto.setTag(contest.getTag());
                    return contestDto;
                })
                .collect(Collectors.toList());
    }

    public boolean isJjim(Long userId, Integer contestId) {
        return contestJjimRepository.findByUserIdAndContestId(userId, contestId).isPresent();
    }
}