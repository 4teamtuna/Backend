package com.example.contest.controller;

import com.example.contest.dto.ContestDto;
import com.example.contest.dto.ContestTeamResponse;
import com.example.contest.dto.TeamResponse;
import com.example.contest.entity.Contest;
import com.example.contest.entity.ContestJjim;
import com.example.contest.repository.ContestRepository;
import com.example.contest.service.ContestJjimService;
import com.example.contest.service.ContestService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.Map;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.Arrays;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class ContestController {

    @Autowired
    private ContestService contestService;
    private final WebClient webClient;
    private final ContestJjimService contestJjimService;
    @Autowired
    private ContestRepository contestRepository;


    @Autowired
    public ContestController(ContestJjimService contestJjimService,WebClient.Builder webClientBuilder) {
        this.contestJjimService = contestJjimService;
        this.webClient = webClientBuilder.baseUrl("http://team-service").build();
    }

    @GetMapping("/contest/{id}") // id에 해당하는 이미지를 제외한 공모전 정보를 반환
    public ContestDto getContest(@PathVariable Integer id) {
        return contestService.getContest(id);
    }
//@GetMapping("/contest/{id}")
//public ContestDto getContest(@PathVariable(name = "id") Integer id, @RequestParam Long userId) {
//
//    Mono<TeamResponse> teamMono = webClient.get()
//            .uri("/api/team/{id}", id)
//            .retrieve()
//            .bodyToMono(TeamResponse.class);
//
//    Mono<ContestTeamResponse> contestTeamDtoMono = webClient.get()
//            .uri("/api/contestTeam/{id}", id)
//            .retrieve()
//            .bodyToMono(ContestTeamResponse.class);
//
//    ContestDto contestDto = Mono.zip(teamMono, contestTeamDtoMono)
//            .map(tuple -> {
//                TeamResponse team = tuple.getT1();
//                ContestTeamResponse contestTeamDto = tuple.getT2();
//
//                ContestDto dto = new ContestDto();
//                dto.setTeams(Arrays.asList(contestTeamDto).stream()
//                        .map(response -> {
//                            ContestTeamDto teamDto = new ContestTeamDto();
//                            tea mDto.setTeamName(response.getTeamName());
//                            teamDto.setId(response.getId());
//                            return teamDto;
//                        })
//                        .collect(Collectors.toList()));
//                dto.setId(Long.valueOf(id)); // Convert Integer to Long
//                // Populate other fields of dto using team and contestTeamDto
//                // ...
//
//                return dto;
//            }).block(); // Convert Mono<ContestDto> to ContestDto
//
//    boolean isJjim = contestJjimService.isJjim(userId, id);
//    contestDto.setJjim(isJjim);
//    return contestDto;
//}

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

}