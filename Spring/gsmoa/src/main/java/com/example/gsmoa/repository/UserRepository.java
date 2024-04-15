package com.example.gsmoa.repository;

import com.example.gsmoa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); // 이메일로 사용자 정보를 가져옴
    User findByNickname(String nickname); // 닉네임으로 사용자 정보를 가져옴
}