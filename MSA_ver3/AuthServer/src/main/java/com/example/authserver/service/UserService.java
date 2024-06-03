package com.example.authserver.service;

import com.example.authserver.dto.UserDto;
import com.example.authserver.dto.UserUpdateDto;
import com.example.authserver.entity.UserEntity;
import com.example.authserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto getUserByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username);
        UserDto userDto = new UserDto();
        userDto.setUsername(userEntity.getUsername());
        userDto.setName(userEntity.getName());
        userDto.setEmail(userEntity.getEmail());
        userDto.setNickname(userEntity.getNickname());
        userDto.setIntroduce(userEntity.getIntroduce());
        userDto.setInterest(userEntity.getInterest());
        return userDto;
    }

    public UserDto updateUser(String username, UserUpdateDto userUpdateDto) {
        UserEntity userEntity = userRepository.findByUsername(username);
        userEntity.setNickname(userUpdateDto.getNickname());
        userEntity.setIntroduce(userUpdateDto.getIntroduce());
        userEntity.setInterest(userUpdateDto.getInterest());
        userRepository.save(userEntity);
        return getUserByUsername(username);
    }
}