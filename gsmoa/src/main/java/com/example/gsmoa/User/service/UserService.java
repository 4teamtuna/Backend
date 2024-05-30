package com.example.gsmoa.User.service;

import com.example.gsmoa.User.dto.UserDto;
import com.example.gsmoa.User.dto.UserUpdateDto;
import com.example.gsmoa.User.entity.UserEntity;
import com.example.gsmoa.User.entity.Interest;
import com.example.gsmoa.User.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

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
        Set<String> interests = userEntity.getInterests().stream().map(Interest::getInterest).collect(Collectors.toSet());
        userDto.setInterests(interests);
        return userDto;
    }

    public UserDto updateUser(String username, UserUpdateDto userUpdateDto) {
        UserEntity userEntity = userRepository.findByUsername(username);
        userEntity.setNickname(userUpdateDto.getNickname());
        userEntity.setIntroduce(userUpdateDto.getIntroduce());
        Set<Interest> interests = userUpdateDto.getInterests().stream().map(interest -> {
            Interest newInterest = new Interest();
            newInterest.setInterest(interest);
            newInterest.setUser(userEntity);
            return newInterest;
        }).collect(Collectors.toSet());
        userEntity.setInterests(interests);
        userRepository.save(userEntity);
        return getUserByUsername(username);
    }
}