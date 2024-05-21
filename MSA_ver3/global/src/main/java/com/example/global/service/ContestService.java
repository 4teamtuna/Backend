package com.example.global.service;

import com.example.global.dto.ContestDto;
import com.example.global.dto.TeamRequestDto;
import com.example.global.entity.Contest;
import com.example.global.repository.ContestRepository;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

@Service
public class ContestService {
    @Autowired
    private ContestRepository contestRepository;

    private final WebClient.Builder webClientBuilder;

    @Autowired
    public ContestService(ContestRepository contestRepository, WebClient.Builder webClientBuilder) {
        this.contestRepository = contestRepository;
        this.webClientBuilder = webClientBuilder;
    }

    public TeamRequestDto getTeam(Long teamId) {
        return webClientBuilder.build()
                .get()
                .uri("http://team-service/teams/" + teamId)
                .retrieve()
                .bodyToMono(TeamRequestDto.class)
                .block();
    }

    public Contest getContest(Long contestId) {
        return webClientBuilder.build()
                .get()
                .uri("http://global-service/contests/" + contestId)
                .retrieve()
                .bodyToMono(Contest.class)
                .block();
    }

    public Contest saveContest(Contest contest) throws Exception {
        if (contestRepository.existsByTitle(contest.getTitle())) {
            throw new Exception("Title already exists.");
        }
        return contestRepository.save(contest);
    }
    public ContestDto getContest(Integer id) {
        Contest contest = contestRepository.findById(id).orElse(null);
        if (contest != null) {
            ContestDto contestDto = new ContestDto();
            contestDto.setContest_id(contest.getContest_id());
            contestDto.setTitle(contest.getTitle());
            contestDto.setHostName(contest.getHostName());
            contestDto.setPeriod(contest.getPeriod());
            contestDto.setPostedDate(contest.getPostedDate());
            contestDto.setTag(contest.getTag());
            contestDto.setViewCount(contest.getViewCount());
            return contestDto;
        }
        return null;
    }

    public byte[] getContestImg(Integer id) throws SQLException {
        Contest contest = contestRepository.findById(id).orElse(null);
        if (contest != null) {
            return contest.getImage();
        }
        return null;
    }






}