package com.example.gsmoa.Contest.controller;

import com.example.gsmoa.Contest.dto.ContestDto;
import com.example.gsmoa.Contest.entity.Contest;
import com.example.gsmoa.Contest.service.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
public class ContestController {

    @Autowired
    private ContestService contestService;

    @GetMapping("/contest/{id}") // id에 해당하는 이미지를 제외한 공모전 정보를 반환
    public ContestDto getContest(@PathVariable(name = "id") Integer id) {
        return contestService.getContest(id);
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

    @GetMapping("/contests/recommend")
    public List<ContestDto> getRecommendedContests(@RequestParam String interests) {
        // Split the interests string into individual tags
        List<String> tags = Arrays.asList(interests.split(","));

        List<Contest> contests = new ArrayList<>();
        for (String tag : tags) {
            // Trim the tag to remove any leading or trailing whitespace
            tag = tag.trim();

            // Find contests with the matching tag and add them to the list
            contests.addAll(contestService.getContestsByInterest(tag));
        }

        List<ContestDto> contestDtos = new ArrayList<>();
        for (Contest contest : contests) {
            ContestDto contestDto = new ContestDto();
            contestDto.setId(contest.getId());
            contestDto.setTitle(contest.getTitle());
            contestDto.setHostName(contest.getHostName());
            contestDto.setPeriod(contest.getPeriod());
            contestDto.setPostedDate(contest.getPostedDate());
            contestDto.setTag(contest.getTag());
            contestDto.setViewCount(contest.getViewCount());
            contestDto.setDetails(contest.getDetails());
            contestDtos.add(contestDto);
        }
        return contestDtos;
    }

}