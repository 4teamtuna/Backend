package com.example.gsmoa.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddUserRequest {
    private String email;
    private String password;
    private String nickname;
    private String phoneNumber;
    private String gender;
    private List<String> interests;
}