package com.example.gsmoa.service;
import lombok.RequiredArgsConstructor;
import com.example.gsmoa.dto.AddUserRequest;
import com.example.gsmoa.entity.User;
import com.example.gsmoa.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(AddUserRequest dto){
        return userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .phoneNumber(dto.getPhoneNumber())
                .gender(dto.getGender())
                .interests(dto.getInterests())
                .build()).getId();
    }
}