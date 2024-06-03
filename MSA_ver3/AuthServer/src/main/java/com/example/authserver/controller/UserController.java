package com.example.authserver.controller;

import com.example.authserver.dto.UserDto;
import com.example.authserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/user/{username}")
    public UserDto getUserEntity(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }
}