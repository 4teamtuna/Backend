package com.example.gsmoa.Contest.controller;

import com.example.gsmoa.Contest.dto.ContestDto;
import com.example.gsmoa.Contest.entity.Contest;
import com.example.gsmoa.Contest.entity.ContestJjim;
import com.example.gsmoa.Contest.service.ContestJjimService;
import com.example.gsmoa.Contest.service.ContestService;
import com.example.gsmoa.TeamChatting.dto.ContestTeamDto;
import com.example.gsmoa.TeamChatting.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

    private final ContestJjimService contestJjimService;

    @Autowired
    public ContestController(ContestJjimService contestJjimService) {
        this.contestJjimService = contestJjimService;
    }

//    @GetMapping("/contest/{id}") // id에 해당하는 이미지를 제외한 공모전 정보를 반환
//    public ContestDto getContest(@PathVariable Integer id) {
//        return contestService.getContest(id);
//    }

    @GetMapping("/contest/{id}") // id에 해당하는 이미지를 제외한 공모전 정보를 반환
    public ContestDto getContest(@PathVariable(name = "id") Integer id) {
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

    @PostMapping("/like")
    public ContestJjim addLike(@RequestBody ContestJjim contestJjim) {
        return contestJjimService.addLike(contestJjim);
    }

    @DeleteMapping("/like/{id}")
    public void removeLike(@PathVariable Long id) {
        contestJjimService.removeLike(id);
    }

    @GetMapping("/like/{userId}")
    public List<ContestJjim> getLikedContests(@PathVariable Long userId) {
        return contestJjimService.getLikedContests(userId);
    }

}