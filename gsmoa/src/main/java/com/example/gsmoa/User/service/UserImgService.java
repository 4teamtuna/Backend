package com.example.gsmoa.User.service;

import com.example.gsmoa.User.entity.UserEntity;
import com.example.gsmoa.User.entity.UserImg;
import com.example.gsmoa.User.repository.UserImgRepository;
import com.example.gsmoa.User.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class UserImgService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserImgRepository userImgRepository;

    public void saveUserImg(Integer userId, MultipartFile file) throws IOException { // 유저 이미지, id를 저장
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            UserImg userImg = new UserImg();
            userImg.setId(Long.valueOf(userId));
            userImg.setUserId(userEntity);
            userImg.setImg(file.getBytes());
            userImgRepository.save(userImg);
        } else {
            // Handle user not found
        }
    }

    public void updateUserImg(Integer userId, MultipartFile file) throws IOException {
        UserImg userImg = userImgRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + userId));
        userImg.setImg(file.getBytes());
        userImgRepository.save(userImg);
    }

    public byte[] getUserImg(Integer userId) { // 유저 id로 이미지를 반환
        UserImg userImg = userImgRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + userId));
        return userImg.getImg();
    }
}