package com.example.global.controller;

import com.example.global.dto.ContestDto;
import com.example.global.entity.Contes
import com.example.global.service.ContestService;
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


@RestController
public class ContestController {

    @Autowired
    private ContestService contestService;

    @GetMapping("/contest/{id}")
    public ContestDto getContest(@PathVariable Integer id) {
        return contestService.getContest(id);
    }

    @GetMapping("/contestImg/{id}")
    public ResponseEntity<byte[]> getContestImg(@PathVariable Integer id) throws SQLException {
        byte[] imgBytes = contestService.getContestImg(id);
        if (imgBytes != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // or other image media type
            return new ResponseEntity<>(imgBytes, headers, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }
}