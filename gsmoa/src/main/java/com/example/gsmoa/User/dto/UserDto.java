package com.example.gsmoa.User.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserDto {

    private String username;
    private String name;
    private String email;
    private String nickname;
    private String introduce;
    private Set<String> interests;
}