package com.example.authserver.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String username;
    private String name;
    private String email;
    private String nickname;
    private String introduce;
    private String interest;
}