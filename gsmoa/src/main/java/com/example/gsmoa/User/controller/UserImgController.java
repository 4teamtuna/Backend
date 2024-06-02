package com.example.gsmoa.User.controller;

import com.example.gsmoa.User.service.UserImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class UserImgController {

    @Autowired
    private UserImgService userImgService;

    @PostMapping("/saveUserImg")
    public ResponseEntity<Void> saveUserImg(@RequestParam Integer userId, @RequestParam MultipartFile file) { // 유저 이미지 저장
        try {
            userImgService.saveUserImg(userId, file);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/userimg/{userId}")
    public ResponseEntity<Void> updateUserImg(@PathVariable Integer userId, @RequestParam MultipartFile file) { // 유저 이미지 업데이트
        try {
            userImgService.updateUserImg(userId, file);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("/userimg/{userId}")
    public ResponseEntity<byte[]> getUserImg(@PathVariable Integer userId) { // 유저 이미지 반환
        byte[] imgBytes = userImgService.getUserImg(userId);
        if (imgBytes != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(imgBytes, headers, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }


}