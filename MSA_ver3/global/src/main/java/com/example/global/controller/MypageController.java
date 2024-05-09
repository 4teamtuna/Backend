package com.example.global.controller;

import com.example.global.dto.UserDto;
import com.example.global.dto.UserUpdateDto;
import com.example.global.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MypageController {

    private final UserService userService;

    @Autowired
    public MypageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/main/my")
    public UserDto mypage() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getUserByUsername(username);
    }

    @PutMapping("/main/my")
    public UserDto updateMyInfo(@RequestBody UserUpdateDto userUpdateDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.updateUser(username, userUpdateDto);
    }
}