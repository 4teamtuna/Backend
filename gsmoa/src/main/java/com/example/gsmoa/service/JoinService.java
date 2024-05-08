package com.example.gsmoa.service;

import com.example.gsmoa.dto.JoinDto;
import com.example.gsmoa.entity.UserEntity;
import com.example.gsmoa.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void joinProcess(JoinDto joinDTO) {

        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();
        String name = joinDTO.getName();
        String nickname = joinDTO.getNickname();
        String interest = joinDTO.getInterest();
        String email = joinDTO.getEmail();
        String introduce = joinDTO.getIntroduce();

        Boolean isExist = userRepository.existsByUsername(username);

        if (isExist) {
            return;
        }

        UserEntity data = new UserEntity();

        data.setUsername(username);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setRole("ROLE_ADMIN");
        data.setName(name);
        data.setNickname(nickname);
        data.setInterest(interest);
        data.setEmail(email);
        data.setIntroduce(introduce);

        userRepository.save(data);
    }
}
