package com.example.gsmoa.Contest.service;

import com.example.gsmoa.Contest.dto.ContestDto;
import com.example.gsmoa.Contest.entity.Contest;
import com.example.gsmoa.Contest.entity.ContestTag;
import com.example.gsmoa.Contest.repository.ContestRepository;
import com.example.gsmoa.TeamChatting.model.Team;
import com.example.gsmoa.TeamChatting.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContestService {
    @Autowired
    private ContestRepository contestRepository;

    @Autowired
    private TeamRepository teamRepository;

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
            contestDto.setDetails(contest.getDetails()); // Add details to the DTO
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
        return teamRepository.findByContestId(contestId);
    }

    public Contest updateContestDetails(Integer id, String details) { // id에 해당하는 공모전의 details를 업데이트 (업데이트용)
        Contest contest = contestRepository.findById(id).orElse(null);
        if (contest != null) {
            contest.setDetails(details);
            return contestRepository.save(contest);
        }
        return null;
    }

//    public List<Contest> getContestsByInterests(List<String> interests) {
//        List<Contest> contests = new ArrayList<>();
//        for (String interest : interests) {
//            // Convert the user's interest to a ContestTag
//            ContestTag tag = ContestTag.valueOf(interest.toUpperCase());
//
//            // Find contests with the matching tag and add them to the list
//            contests.addAll(contestRepository.findByTagsContaining(tag.getDisplayName()));
//        }
//        return contests;
//    }

    public List<Contest> getContestsByInterest(String interest) {
        // Find contests with the matching tag
        List<Contest> contests = contestRepository.findByTagsContaining(interest);

        return contests;
    }


}
