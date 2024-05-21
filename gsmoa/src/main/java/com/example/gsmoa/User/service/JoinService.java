package com.example.gsmoa.User.service;

import com.example.gsmoa.User.dto.JoinDto;
import com.example.gsmoa.User.entity.UserEntity;
import com.example.gsmoa.User.entity.Interest;
import com.example.gsmoa.User.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

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
        Set<String> interests = joinDTO.getInterests();
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
        Set<Interest> interestEntities = interests.stream().map(interest -> {
            Interest newInterest = new Interest();
            newInterest.setInterest(interest);
            newInterest.setUser(data);
            return newInterest;
        }).collect(Collectors.toSet());
        data.setInterests(interestEntities);
        data.setEmail(email);
        data.setIntroduce(introduce);

        userRepository.save(data);
    }
}