package com.example.gsmoa.User.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UserImgDto {
    private Integer userId;
    private MultipartFile file;
}
