package com.example.gsmoa.Contest.controller;

import com.example.gsmoa.Contest.dto.ContestDto;
import com.example.gsmoa.Contest.dto.ContestRecommendDto;
import com.example.gsmoa.Contest.entity.Contest;
import com.example.gsmoa.Contest.entity.ContestJjim;
import com.example.gsmoa.Contest.repository.ContestRepository;
import com.example.gsmoa.Contest.service.ContestJjimService;
import com.example.gsmoa.Contest.service.ContestService;
import com.example.gsmoa.TeamChatting.dto.ContestTeamDto;
import com.example.gsmoa.TeamChatting.model.Team;
import com.example.gsmoa.User.repository.UserRepository;
import com.example.gsmoa.User.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.Map;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class ContestController {

    @Autowired
    private ContestService contestService;

    private final ContestJjimService contestJjimService;
    @Autowired
    private ContestRepository contestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    public ContestController(ContestJjimService contestJjimService) {
        this.contestJjimService = contestJjimService;
    }

//    @GetMapping("/contest/{id}") // id에 해당하는 이미지를 제외한 공모전 정보를 반환
//    public ContestDto getContest(@PathVariable Integer id) {
//        return contestService.getContest(id);
//    }

    @GetMapping("/contest/{id}") // id에 해당하는 이미지를 제외한 공모전 정보를 반환
    public ContestDto getContest(@PathVariable(name = "id") Integer id, @RequestParam Long userId) {
        ContestDto contestDto = contestService.getContest(id);
        List<Team> teams = contestService.getTeamsByContestId(id);
        List<ContestTeamDto> teamNames = teams.stream()
                .map(team -> {
                    ContestTeamDto dto = new ContestTeamDto();
                    dto.setTeamName(team.getTeamName());
                    dto.setId(team.getId());
                    return dto;
                })
                .collect(Collectors.toList());
        contestDto.setTeams(teamNames);

        boolean isJjim = contestJjimService.isJjim(userId, id);
        contestDto.setJjim(isJjim);
        return contestDto;
    }

    @GetMapping("/contestImg/{id}") // id에 해당하는 공모전 이미지 반환
    public ResponseEntity<byte[]> getContestImg(@PathVariable(name = "id") Integer id) throws SQLException {
        byte[] imgBytes = contestService.getContestImg(id);
        if (imgBytes != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(imgBytes, headers, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/contestImgs/{startId}/{endId}") // startId부터 endId까지의 공모전 이미지 반환, 0/0이면 모든 공모전 이미지 반환
    public List<ResponseEntity<byte[]>> getContestImgsInRange(@PathVariable(name = "startId") Integer startId, @PathVariable(name = "endId") Integer endId) throws SQLException {
        List<byte[]> imgBytesList = contestService.getContestImgsInRange(startId, endId);
        List<ResponseEntity<byte[]>> responses = new ArrayList<>();

        for (byte[] imgBytes : imgBytesList) {
            if (imgBytes != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG);
                responses.add(new ResponseEntity<>(imgBytes, headers, HttpStatus.OK));
            }
        }
        return responses;
    }

    @GetMapping("/contestImgsURL/{startId}/{endId}") // startId부터 endId까지의 공모전 이미지 반환, 0/0이면 모든 공모전 이미지 반환
    public List<String> getContestImgsInRangeURL(@PathVariable(name = "startId") Integer startId, @PathVariable(name = "endId") Integer endId) throws SQLException {
        List<byte[]> imgBytesList = contestService.getContestImgsInRange(startId, endId);
        List<String> imageUrls = new ArrayList<>();

        for (byte[] imgBytes : imgBytesList) {
            if (imgBytes != null) {
                String imageName = "image_" + System.currentTimeMillis() + ".jpg";
                Path path = Paths.get("images/" + imageName);
                try {
                    Files.createDirectories(path.getParent());
                    Files.write(path, imgBytes);
                } catch (IOException e) {
                    System.err.println("Error writing file: " + e.getMessage());
                }

                String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/images/")
                        .path(imageName)
                        .toUriString();

                imageUrls.add(imageUrl);
            }
        }
        return imageUrls;
    }

    @PostMapping("/contest/like")
    public ContestJjim addLike(@RequestBody ContestJjim contestJjim) {
        return contestJjimService.saveContestJjim(contestJjim);
    }

    @DeleteMapping("/contest/like")
    public void removeLike(@RequestBody Map<String, Object> params) {
        Long userId = Long.valueOf((Integer) params.get("userId"));
        Integer contestId = (Integer) params.get("contestId");
        contestJjimService.removeLikedContest(userId, contestId);
    }

    @GetMapping("/contest/like/{userId}")
    public List<ContestDto> getLikedContestsDetails(@PathVariable Long userId) {
        return contestJjimService.getLikedContestsDetails(userId);
    }

    @GetMapping("/contests/recommend/{userId}")
    public List<ContestRecommendDto> getRecommendedContests(@PathVariable(name = "userId") Integer userId) {
        // userid로 interest 정보 가져옴
        //System.out.println("userId: " + userId);
        List<String> interests = userService.getUserInterests(userId);
        //System.out.println("interests: " + interests);

        List<Contest> contests = new ArrayList<>();
        if (interests.isEmpty()) {
            // 일치하는 관심사가 없으면 랜덤 생성
            contests = contestRepository.findAllHavePeriod();
        } else {
            for (String interest : interests) {
                contests.addAll(contestService.getContestsByInterest(interest));
            }
        }
        //System.out.println("contests: " + contests);

        List<ContestRecommendDto> recommends = new ArrayList<>();
        for (Contest contest : contests) {
            ContestRecommendDto recommenddto = new ContestRecommendDto();
            recommenddto.setId(contest.getId());
            recommenddto.setTitle(contest.getTitle());
            try {
                recommenddto.setImage(contestService.getContestImg(contest.getId()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            recommends.add(recommenddto);
        }
        return recommends;

//        List<ContestDto> contestDtos = new ArrayList<>();
//        for (Contest contest : contests) {
//            ContestDto contestDto = new ContestDto();
//            contestDto.setId(contest.getId());
//            contestDto.setTitle(contest.getTitle());
//            contestDto.setHostName(contest.getHostName());
//            contestDto.setPeriod(contest.getPeriod());
//            contestDto.setPostedDate(contest.getPostedDate());
//            contestDto.setTag(contest.getTag());
//            contestDto.setViewCount(contest.getViewCount());
//            contestDto.setDetails(contest.getDetails());
//            contestDtos.add(contestDto);
//        }
//        return contestDtos;
    }

}