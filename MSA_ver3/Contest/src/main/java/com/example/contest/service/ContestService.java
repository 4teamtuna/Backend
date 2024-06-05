package com.example.contest.service;

import com.example.contest.dto.ContestDto;
import com.example.contest.entity.Contest;
import com.example.contest.repository.ContestRepository;
import com.example.contest.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ContestService {
    @Autowired
    private ContestRepository contestRepository;

    @Autowired
    private RestTemplate restTemplate;


    public Contest saveContest(Contest contest) throws Exception { // Contest 객체 저장
        if (contestRepository.existsByTitle(contest.getTitle())) {
            throw new Exception("Title already exists.");
        }
        return contestRepository.save(contest);
    }
    public ContestDto getContest(Integer id) { // id에 해당하는 이미지를 제외한 공모전 정보를 반환
        Contest contest = contestRepository.findById(id).orElse(null);
        if (contest != null) {
            ContestDto contestDto = new ContestDto();
            contestDto.setId(contest.getId());
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

    public byte[] getContestImg(Integer id) throws SQLException { // id에 해당하는 공모전 이미지 반환
        Contest contest = contestRepository.findById(id).orElse(null);
        if (contest != null) {
            return contest.getImage();
        }
        return null;
    }

    public List<byte[]> getContestImgsInRange(Integer startId, Integer endId) throws SQLException { // startId부터 endId까지의 공모전 이미지 반환, 0/0이면 모든 공모전 이미지 반환
        List<Contest> contests;
        if (startId == 0 && endId == 0) {
            contests = contestRepository.findAll();
        } else {
            if (startId <= 0) // startId가 0보다 작거나 같으면 1로 설정(db의 id는 1부터 시작)
                startId = 1;
            contests = contestRepository.findByIdBetween(startId, endId);
        }
        List<byte[]> images = new ArrayList<>();
        for (Contest contest : contests) {
            images.add(contest.getImage());
        }
        return images;
    }

    public List<Team> getTeamsByContestId(Integer contestId) {
        String teamServiceUrl = "http://team-chatting-service/teams/contest/" + contestId;
        ResponseEntity<Team[]> response = restTemplate.getForEntity(teamServiceUrl, Team[].class);
        return Arrays.asList(response.getBody());
    }

    public ContestDto getContestById(Integer id) {
        String contestServiceUrl = "http://contest-service/contests/" + id;
        ResponseEntity<ContestDto> response = restTemplate.getForEntity(contestServiceUrl, ContestDto.class);
        return response.getBody();
    }



}