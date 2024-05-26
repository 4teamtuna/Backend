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
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@RestController
public class ContestController {

    @Autowired
    private ContestService contestService;

//    @GetMapping("/contest/{id}") // id에 해당하는 이미지를 제외한 공모전 정보를 반환
//    public ContestDto getContest(@PathVariable Integer id) {
//        return contestService.getContest(id);
//    }

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

}