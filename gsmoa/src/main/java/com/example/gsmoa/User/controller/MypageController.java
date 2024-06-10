package com.example.gsmoa.User.controller;

import com.example.gsmoa.User.dto.UpdateIntroduce;
import com.example.gsmoa.User.dto.UserDto;
import com.example.gsmoa.User.dto.UserUpdateDto;
import com.example.gsmoa.User.entity.UserEntity;
import com.example.gsmoa.User.repository.UserRepository;
import com.example.gsmoa.User.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/main/update/introduce/{userId}")
    public UserEntity updateIntroduce(@RequestBody UpdateIntroduce updateIntroduce, @PathVariable Integer userId) {
        return userService.updateUserIntroduce(userId, updateIntroduce);
    }
}