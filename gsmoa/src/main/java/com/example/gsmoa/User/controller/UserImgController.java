package com.example.gsmoa.User.controller;

import com.example.gsmoa.User.dto.UserImgDto;
import com.example.gsmoa.User.service.UserImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class UserImgController {

    @Autowired
    private UserImgService userImgService;

    @PostMapping("/SaveUserImg")
    public ResponseEntity<Void> saveUserImg( UserImgDto userImgDto) { // 유저 이미지 저장
        try {
            userImgService.saveUserImg(userImgDto.getUserId(), userImgDto.getFile());
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/UpdateUserImg")
    public ResponseEntity<Void> updateUserImg( UserImgDto userImgDto) { // 유저 이미지 업데이트
        try {
            userImgService.updateUserImg(userImgDto.getUserId(), userImgDto.getFile());
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/GetUserImg/{userId}")
    public ResponseEntity<byte[]> getUserImg(@PathVariable(name = "userId") Integer userId) { // 유저 이미지 반환
        byte[] imgBytes = userImgService.getUserImg(userId);
        if (imgBytes != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(imgBytes, headers, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }
}