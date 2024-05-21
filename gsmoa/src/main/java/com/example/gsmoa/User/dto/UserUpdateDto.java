package com.example.gsmoa.User.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserUpdateDto {

    private String nickname;
    private String introduce;
    private Set<String> interests;
}