package com.example.authserver.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDto {
    private String nickname;
    private String introduce;
    private String interest;
}