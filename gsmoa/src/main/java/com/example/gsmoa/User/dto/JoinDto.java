package com.example.gsmoa.User.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinDto {

    private String username;
    private String password;

    private String name;
    private String email;
    private String nickname;
    private String introduce;
    private String interest;
}
